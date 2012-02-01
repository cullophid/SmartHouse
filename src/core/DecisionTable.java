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
    private HashMap<KeyList,Float> on,off,temp;
    private Statement stmt;
    private Connection conn;
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
        Config.loadConfig();
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
            EventList eventlist = new EventList(false); 
            long time;
            long start = System.currentTimeMillis();
            String type;
            KeyList keylist;
            on = new HashMap<KeyList,Float>();
            off = new HashMap<KeyList,Float>();
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
                   temp = (result.getBoolean("status"))?on:off; 
                    if(time>lastevent+Config.patternInterval){
                        eventlist = new EventList();
                    }
                    keylist = new KeyList(eventlist);
                    keylist.add(id);

                    if(temp.containsKey(keylist)){
                        System.out.print("Key: ");
                        keylist.printValues();
                        System.out.println();
                        temp.put(keylist,temp.get(keylist)+1);
                    }
                    else{
                        temp.put(keylist,1f);
                        System.out.print("Key: ");
                        keylist.printValues();
                        System.out.println();
                    }
                
                 
                }
            } 
            KeyList ksub;
                       long end = System.currentTimeMillis();
            long runtime = end-start;
            System.out.println("rows : "+i);
            System.out.println("runtime = "+runtime);
          /* 
             for(KeyList k : on.keySet()){
                ksub = k.subList(0,k.size()-2);
                on.put(k,on.get(k)/denominator.get(ksub));
            }
             for(KeyList k : off.keySet()){
                ksub = k.subList(0,k.size()-2);
                off.put(k,off.get(k)/denominator.get(ksub));
            }
 
            */
            System.out.println("printing table on");
            int switches = 0;
            for(KeyList k : on.keySet()){
                System.out.print("key: ");
                k.printValues();
                System.out.println("value: "+on.get(k)); 
                switches += on.get(k);
            }
            System.out.println("total switch events : "+switches);

            System.out.println("printing table off");
            switches = 0;
            for(KeyList k : off.keySet()){
                System.out.print("key: ");
                k.printValues();
                System.out.println("value: "+off.get(k)); 
                switches += off.get(k);
            }
            System.out.println("total switch events : "+switches);
 
        }
        catch (SQLException se){
            System.out.println("SQLException: " + se.getMessage());
            System.out.println("SQLState: " + se.getSQLState());
            System.out.println("VendorError: " + se.getErrorCode());
 
        }

        
    }

}
