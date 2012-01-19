package smarthouse;
//hopefully non conflicting test commit
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import markov.MarkovTable;
import config.Config;

public class SmartHouse{
	
    Connection conn = null;
    Statement stmt;
    
    public static void main(String[] args) {        
        Map<Integer, Boolean> lamps = new HashMap<Integer, Boolean>();
        lamps.put(3, false);
        lamps.put(7, false);
        lamps.put(42, true);
        for(int key : lamps.keySet()) {
            System.out.println(key + "-->" + lamps.get(key));
        }
    }
    /*
     * Constructor for the class SmartHouse
     * Handles the input and output for the ai
     */
    public SmartHouse(){
        Config config = new Config();
        try {
            Class.forName("com.mysql.jdbc.Driver");//load the mysql driver
            conn = DriverManager.getConnection("jdbc:mysql://localhost/kiiib?user=KIIIB&password=42");//connect to the database
            stmt = conn.createStatement();
            MarkovTable onMarkov = new MarkovTable(true);
            MarkovTable offMarkov = new MarkovTable(false);
            onMarkov.generateMarkovTable();
            offMarkov.generateMarkovTable();
            onMarkov.printMarkovTable();
            offMarkov.printMarkovTable(); 
            
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
    /*
     * Method called when a sensorevent occurs in the simulator
     */
    public void sensorEvent(int sensorId){
        try{
            System.out.println("Sensor "+sensorId+" fired!");        
            stmt.executeUpdate("INSERT INTO sensor_events VALUES("+sensorId+",NOW())");
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
}
