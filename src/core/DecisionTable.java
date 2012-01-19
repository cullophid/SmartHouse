package core;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.HashMap;
import config.Config;
import core.KeyList;
import java.util.LinkedList;
import java.util.ArrayList;

public class DecisionTable{
    private HashMap<KeyList,Integer> decisionTable;
    private Statement stmt;
    private Connection conn;
    private ArrayList<Integer> sensors,switches;
    private LinkedList<Integer> eventBuffer; // holds the last n sensorevents, n = memoryDepth 
    /*
     * Class constructor
     */
    public DecisionTable(){
        connect2DB();
        generateDecisionTable();
            }
    /*
     * Connects to the database, and initiates the statement object to be used later
     */
    public void connect2DB(){
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
    public void generateDecisionTable(){

        try{

            decisionTable = new HashMap<KeyList,Integer>();  
            eventBuffer = new LinkedList<Integer>();
            sensors = new ArrayList<Integer>();
            switches = new ArrayList<Integer>();

            // get list of sensors and switches
            ResultSet result = stmt.executeQuery("SELECT DISTINCT id FROM sensor_events"); 
            while(result.next()){
                sensors.add(result.getInt("id"));
            }
            result = stmt.executeQuery("SELECT DISTINCT id FROM switch_events"); 
            while(result.next()){
                switches.add(result.getInt("id"));
            }


            fillTable(Config.getDepth(),new KeyList());
 
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    public void fillTable(int i, KeyList keys){
       if(i==Config.getDepth()){
            for(int sensor : sensors){
                keys.add(i, sensor);        
                for (int swtich : switches){
                    // do stuff
                }
            }
       } 
       else {
            for(int sensor : sensors){
                keys.add(sensor);
                fillTable(i++,keys); 
           }
       }
    }

}
