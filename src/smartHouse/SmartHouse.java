package smartHouse;
//hopefully non conflicting test commit
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import timer.Sleeper;
import timer.TimeoutEvent;
import timer.TimeoutListener;
import timer.Timer;

import events.*;
import markov.Correlation;
import markov.MarkovTable;
import config.Config;
import ai.impl.Ai;

public class SmartHouse implements TimeoutListener {

    private static boolean debug = true;
    Connection conn = null;
    Statement stmt;
    Ai ai;
    EventList events;
    Correlation correlation;
    Timer timer;
    List<Integer> timeout;
    int onTime;
    int punishmentTimeout;
    Map<Integer, Integer> firstSensorAfterTimeout;
    
    
    /*
     * Constructor for the class SmartHouse
     * Handles the input and output for the ai
     */
    public SmartHouse(){
        //Config.loadConfig();
        try {
            debug = Config.debug;
            Class.forName("com.mysql.jdbc.Driver");//load the mysql driver
            conn = DriverManager.getConnection("jdbc:mysql://localhost/kiiib?user=KIIIB&password=42");//connect to the database
            stmt = conn.createStatement();
            MarkovTable onMarkov = new MarkovTable(true);
            MarkovTable offMarkov = new MarkovTable(false);
//            onMarkov.generateMarkovTable();
//            offMarkov.generateMarkovTable();
//            onMarkov.printMarkovTable();
//            offMarkov.printMarkovTable();
            correlation = new Correlation();
            
            events = new EventList();
            timer = new Timer();
            timeout = new ArrayList<Integer>(10);
            onTime = Config.defaultOnTime;
            punishmentTimeout = Config.punishmentTimeout;
            firstSensorAfterTimeout = new HashMap<Integer, Integer>();
        }
        catch (SQLException se){
            System.out.println("SQLException: " + se.getMessage());
            System.out.println("SQLState: " + se.getSQLState());
            System.out.println("VendorError: " + se.getErrorCode());
 
        }
        catch (Exception e){
             e.printStackTrace();
       }
    }
    
    public SmartHouse(Ai ai) {
        this();
        this.ai = ai;
    }
    /*
     * Method called when a sensorevent occurs in the simulator
     */
    public void sensorEvent(int sensorId){
        try{
            System.out.println("Sensor "+sensorId+" fired!"); 
            events.sensorEvent(sensorId);
            if (!debug)
                stmt.executeUpdate("INSERT INTO sensor_events VALUES("+sensorId+",NOW())");
            
            for (int sw : timeout) {
                if (!firstSensorAfterTimeout.containsKey(sw))
                    firstSensorAfterTimeout.put(sw, sensorId);
            }
            for (int sw : correlation.getSwitches(sensorId, 0.5f)) {
                if (!timeout.contains(sw)) {
                    timer.updateTimeout(sw, onTime, this);
                }
            }
        }
        catch(SQLException se){
            System.out.println("SQLException: " + se.getMessage());
            System.out.println("SQLState: " + se.getSQLState());
            System.out.println("VendorError: " + se.getErrorCode());
        }
    }
    /*
     * Method called when a switch event occurs in the simulator
     */
    public void switchEvent(int switchId, int status){
        try{
            System.out.println("Switch "+switchId+" turned "+status);
//            System.out.println(events);
            boolean cmd = (status == 1) ? true : false;
            
            if (cmd) {
                if (timeout.contains(switchId)) {
                    timeout.remove((Object) switchId);
                    timer.stop(switchId);
                    if (firstSensorAfterTimeout.containsKey(switchId))
                        correlation.increaseCorrelation(switchId, firstSensorAfterTimeout.get(switchId));
                }
                ai.on(switchId);
                timer.setTimeout(switchId, onTime, this);
                
            } else {
                ai.off(switchId);
            }
            if (!debug)
                stmt.executeUpdate("INSERT INTO switch_events VALUES("+switchId+","+status+",NOW())");
        }
        catch(SQLException se){
            System.out.println("SQLException: " + se.getMessage());
            System.out.println("SQLState: " + se.getSQLState());
            System.out.println("VendorError: " + se.getErrorCode());
        }
    }
    
    private Map<Integer, Boolean> testMap = new HashMap<Integer, Boolean>();
    
    public Map<Integer, Boolean> markovLookup(int sensorId) {
        Map<Integer, Boolean> switches = new HashMap<Integer, Boolean>();
                
        boolean b = (testMap.containsKey(sensorId)) ? testMap.get(sensorId) : true;
        testMap.put(sensorId, !b);
        
        switches.put(sensorId, b);
        
        return switches;
    }

    public void TimeoutEventOccurred(TimeoutEvent event) {
        System.out.println("I should probably turn off the light now");
        int id = (Integer) event.getSource(); 
        if (timeout.contains(id) && events.getLastEvent() != null) {
            correlation.reduceCorrelation(id, events.getLastEvent().getID());
            timeout.remove(event.getSource());
        } else {
            ai.off(id);
            timeout.add(id);
            timer.setTimeout(id, punishmentTimeout, this);
        }
    }
}
