package SmartHouse;
//hopefully non conflicting test commit
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import timer.Sleeper;
import timer.TimeoutEvent;
import timer.TimeoutListener;
import timer.Timer;

import events.*;
import markov.Correlation;
import markov.MarkovTable;
import ai.impl.Ai;

public class SmartHouse implements TimeoutListener {

    private static boolean debug = true;
    Connection conn = null;
    Statement stmt;
    Ai ai;
    EventList events;
    Correlation correlation;
    Timer timer;
    long t = 5000;
    
    /*
     * Constructor for the class SmartHouse
     * Handles the input and output for the ai
     */
    public SmartHouse(){
        try {
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
            correlation.generateCorrelation();
            
            events = new EventList();
            timer = new Timer();
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
            
            for (int sw : correlation.getSwitches(sensorId, 0.5f)) {
                timer.updateTimeout(sw, t, this);
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
                ai.on(switchId);
                timer.setTimeout(switchId, t, this);
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
        ai.off((Integer) event.getSource());
    }
}
