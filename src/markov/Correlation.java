package markov;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import events.*;


public class Correlation {

    private Statement stmt;
    private Connection conn;
    private long correlation_interval = 7*1000;
    private Map<Integer, Map<Integer, Float>> correlation;
    
    public Correlation () {
        correlation = new HashMap<Integer, Map<Integer, Float>>();
        try {
            Class.forName("com.mysql.jdbc.Driver");//load the mysql driver
            conn = DriverManager.getConnection("jdbc:mysql://localhost/kiiib?user=KIIIB&password=42");//connect to the database
            stmt = conn.createStatement();
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
    
    public float getCorrelation(int switchId, int sensorId) {
        if (! correlation.containsKey(switchId))
            return 0;
        
        if (! correlation.get(switchId).containsKey(sensorId))
            return 0;
        
        return correlation.get(switchId).get(sensorId);
    }

    public static void incrementSwitchCount(Map<Integer, Integer> switch_count, int id) {
        if (!switch_count.containsKey(id))
            switch_count.put(id, 1);
        else
            switch_count.put(id, switch_count.get(id) + 1);
    }
    
    public static void incrementSensorCount(Map<Integer, Map<Integer, Integer>> sensor_count, int switchId, int sensorId) {
        if (!sensor_count.containsKey(switchId)) {
            sensor_count.put(switchId, new HashMap<Integer, Integer>());
        }
        
        Map<Integer, Integer> map = sensor_count.get(switchId);
        if (!map.containsKey(sensorId)) {
            map.put(sensorId, 1);
        } else {
            map.put(sensorId, map.get(sensorId) + 1);
        }
    }
    
    public void generateCorrelation() {
        
        try {
            Map<SwitchEvent, Set<Integer>> switch_events = new HashMap<SwitchEvent, Set<Integer>>();
            Map<Integer, Integer> switch_count = new HashMap<Integer, Integer>();
            Map<Integer, Map<Integer, Integer>> sensor_count = new HashMap<Integer, Map<Integer, Integer>>();
            LinkedList<SwitchEvent> gc = new LinkedList<SwitchEvent>();
            
            ResultSet result = stmt.executeQuery("(select id,timestamp,'sensor' AS type, '0' AS status from sensor_events) union (select id,timestamp,'switch' AS type,status from switch_events) order by timestamp;");
            int i = 0;
            while(result.next()) {
                int id = result.getInt("id");
                long ts = result.getTimestamp("timestamp").getTime();
                if (result.getString("type").equals("switch")) {
                    boolean cmd = (result.getInt("status") == 1) ? true : false;
                    if (cmd) {
                        SwitchEvent s = new SwitchEvent(id, ts, cmd);
                        switch_events.put(s, new HashSet<Integer>());
                        gc.addLast(s);
                    }
                } else if (result.getString("type").equals("sensor")) {
                    for (SwitchEvent e : switch_events.keySet()) {
                        if (e.getTS() + correlation_interval > ts) {
                            switch_events.get(e).add(id);
                        }
                    }
                }

                while(gc.size() > 0 && gc.getFirst().getTS() + correlation_interval < ts) {
                    SwitchEvent se = gc.getFirst();
                    incrementSwitchCount(switch_count, se.getID());
                    for (int sensor : switch_events.get(se))
                        incrementSensorCount(sensor_count, se.getID(), sensor);
                    gc.removeFirst();
                    switch_events.remove(se);
                }
                
                for(int sw : sensor_count.keySet()) {
                    Map<Integer, Float> map = new HashMap<Integer, Float>();
                    for (int se : sensor_count.get(sw).keySet()) {
                        map.put(se, (float) sensor_count.get(sw).get(se) / switch_count.get(sw)); 
                    }
                    correlation.put(sw, map);
                }
            }
            while(gc.size() > 0) {
                SwitchEvent se = gc.getFirst();
                incrementSwitchCount(switch_count, se.getID());
                for (int sensor : switch_events.get(se))
                    incrementSensorCount(sensor_count, se.getID(), sensor);
                gc.removeFirst();
                switch_events.remove(se);
            }            
        } catch (SQLException se){
            se.printStackTrace();
            System.out.println("SQLException: " + se.getMessage());
            System.out.println("SQLState: " + se.getSQLState());
            System.out.println("VendorError: " + se.getErrorCode());
        }        
    }
    
    public Set<Integer> getSwitches() {
        return new TreeSet<Integer>(correlation.keySet());
    }
    
    public Set<Integer> getSensors() {
        Set<Integer> sensors = new TreeSet<Integer>();
        for(int sw : correlation.keySet()) {
            sensors.addAll(correlation.get(sw).keySet());
        }
        return sensors;
    }
    
    /**
     * get a list of switches, that have a correlation with a sensor above the threshold
     * @param sensor
     * @param threshold 0 <= x <= 1
     * @return
     */
    public List<Integer> getSwitches(int sensor, float threshold) {
        List<Integer> list = new LinkedList<Integer>();
        for (int sw : correlation.keySet()) {
            Map<Integer, Float> map = correlation.get(sw);
            if (!map.containsKey(sensor))
                continue;
            
            if (map.get(sensor) > threshold)
                list.add(sw);
        }
        return list;
    }
        
    public String toString() {
        StringBuilder sb = new StringBuilder(1024);
        sb.append("Corr.\t");
        for (int sw : getSensors())
            sb.append(sw + "\t");
        sb.append("\n");
        
        for (int sw : getSwitches()) {
            sb.append(sw + "\t");
            for (int se : getSensors()) {
                if (correlation.get(sw).containsKey(se)) {
                    float f = correlation.get(sw).get(se);
                    if (f >= 0.5)
                        sb.append("*");
                    sb.append(String.format("%.2f\t", f));
                } else {
                    sb.append("0\t");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }
    
}
