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
import events.*;

public class DecisionTable{
    private HashMap<KeyList,Float> decisionTable;
    private Statement stmt;
    private Connection conn;
    private ArrayList<Integer> sensors,switches;
    private LinkedList<Integer> eventBuffer; // holds the last n sensorevents, n = memoryDepth 
    /**
     * temporary main method for testing puposes
     * @author Andreas Møller & David Emil Lemvigh
     **/
    public static void main(String[] args){
        DecisionTable dt = new DecisionTable();
    }
    /**
     * Class constructor
     * @author Andreas Møller & David Emil Lemvigh
     **/
    public DecisionTable(){
        connect2DB();
        generateDecisionTable();
            }
    /**
     *
     * Connects to the database, and initiates the statement object to be used later
     * @author Andreas Møller & David Emil Lemvigh
     **/
    public void connect2DB(){
         try {
            System.out.println("Trying to connect to the database");
            Class.forName("com.mysql.jdbc.Driver");//load the mysql driver
            conn = DriverManager.getConnection("jdbc:mysql://localhost/kiiib?user=KIIIB&password=42");//connect to the database
            stmt = conn.createStatement();
            System.out.println("connection established");
           

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
    /**
     * generates the decision table
     * @author Andreas Møller & David Emil Lemvigh
     * */
    public void generateDecisionTable(){
        System.out.println("generating decisiontable");
        try {
            long lastevent = 0;
            int val,id;
            int i = 0;
            EventList eventlist = new EventList(); 
            long time;
            long start = System.currentTimeMillis();
            String type;
            KeyList keylist;
            decisionTable = new HashMap<KeyList,Float>();
            HashMap<KeyList,Integer> denominator = new HashMap<KeyList,Integer>();   
            System.out.println("fetching data from db");
            ResultSet result = stmt.executeQuery("(select id,timestamp,'sensor' AS type, '0' AS status from sensor_events) union (select id,timestamp,'switch' AS type,status from switch_events) order by timestamp;"); 
            System.out.println(" iterating resultset");
            while(result.next()){
                i++;
                id = result.getInt("id");
                time = result.getTimestamp("timestamp").getTime();
                type = result.getString("type");
                //System.out.println("event : "+id+" type: "+type+" time : "+time);
                if(type.equals("sensor")){
                    eventlist.add(new SensorEvent(id,time));
                    keylist = new KeyList(eventlist);
                    if (denominator.containsKey(keylist)){
                        denominator.put(keylist,denominator.get(keylist)+1);

                    }
                    else{
                        denominator.put(keylist,1);
                    }       
                    lastevent = time;
                }
                else if(type.equals("switch")){
                    if(time<=lastevent+Config.patternInterval){
                        keylist = new KeyList(eventlist);
                        keylist.add(id);
                        if(decisionTable.containsKey(keylist)){
                            decisionTable.put(keylist,decisionTable.get(keylist)+1);
                        }
                        else{
                            decisionTable.put(keylist,1f);
                        }
                    }
                }
            } 
            KeyList ksub;
                       long end = System.currentTimeMillis();
            long runtime = end-start;
            System.out.println("rows : "+i);
            System.out.println("runtime = "+runtime);
           
             for(KeyList k : decisionTable.keySet()){
                ksub = k.subList(0,k.size()-2);
                decisionTable.put(k,decisionTable.get(k)/denominator.get(ksub));
            }
            
            for(KeyList k : decisionTable.keySet()){
                System.out.print("key: ");
                k.printValues();
                System.out.println("value: "+decisionTable.get(k)); 
            }
        }
        catch (SQLException se){
            System.out.println("SQLException: " + se.getMessage());
            System.out.println("SQLState: " + se.getSQLState());
            System.out.println("VendorError: " + se.getErrorCode());
 
        }

        
    }

}
