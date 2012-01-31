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
        //generateDecisionTable();
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
        int lastevent = 0;
        int val,id;
        EventList eventlist = new EventList(); 
        long time;
        String type;
        decisionTable = new HashMap<KeyList,Integer>();
        KeyList templist;
        HashMap<KeyList,Integer> denominator = new HashMap<KeyList,Integer>();   
        ResultSet result = stmt.executeQuery("(select id,timestamp,'sensor' AS type, '0' AS status from sensor_events) union (select id,timestamp,'switch' AS type,status from switch_events) order by timestamp;"); 
        while(result.next()){
            id = result.getInt("id");
            time = result.getTimestamp("timestamp").getTime();
            type = result.getString("type");
            if(type.equals("sensor")){
                eventlist.add(new SensorEvent(id,time));
                val = denominator.get(new KeyList(eventlist));
                if (val == null){
                    denominator.put(new KeyList(eventlist),1);
                }
                else{
                    denominator.put(new KeyList(eventlist),val+1);
                }       
                lastevent = time;
            }
            else if(type.equals("switch")){
                if(time<=lastevent+Config.patternInterval){
                    templist = new KeyList(eventlist);
                    templist.add(id);
                    val = decisionTable.get(templist);
                    if(val == null){
                        decisionTable.put(templist,1);
                    }
                    else{
                        decisionTable.put(templist,val+1);
                    }
                }
            }
        } 
    }

}
