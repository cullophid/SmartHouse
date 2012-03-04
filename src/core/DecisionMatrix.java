package core;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.HashMap;
import config.Config;
import core.KeyList;

import java.util.Date;
import java.util.LinkedList;
import java.util.ArrayList;
import events.*;

/**
 * @author Andreas
 */
public class DecisionMatrix{
    public HashMap<KeyList, Float> on, off;    
    private HashMap<KeyList, Integer> count;    
    private Statement stmt;
    private Connection conn;
    private LinkedList<Integer> eventBuffer; // holds the last n sensorevents, n = memoryDepth 
    public ArrayList<Integer> switches,sensors;
    
    /**
     * temporary main method for testing puposes
     * @author Andreas
     **/
    public static void main(String[] args){
        Config.loadConfig();
        DecisionMatrix dm = new DecisionMatrix();
    }

    public DecisionMatrix(){
        connect2DB();
        generateBasicMatrices();
        if(Config.useZones)
            generateZoneMatrices();
       // printTables();
        System.out.println("switches");
        for(int i : switches){
           System.out.println(i);
        } 
        System.out.println("sensors");
        for(int i : sensors){
           System.out.println(i);
        }
        printMatrices();
    }
    /**
     * Connects to the database, and initiates the statement object to be used later
     * @author Andreas
     **/
    public void connect2DB(){
         try {
            System.out.println("Trying to connect to the database");
            Class.forName("com.mysql.jdbc.Driver");//load the mysql driver
            conn = DriverManager.getConnection(Config.DB);//connect to the database
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
     * generates the basic tables on / off
     * @author Andreas
     * */
    public void generateBasicMatrices(){
        System.out.println("generating basic matrices");
        try {
			HashMap<KeyList,Float> temp;


            switches = new ArrayList<Integer>();
            sensors = new ArrayList<Integer>();
           
            ResultSet result = stmt.executeQuery("SELECT DISTINCT id FROM sensor_events"); 
            while(result.next()){
                sensors.add(result.getInt("id"));
            }
            result = stmt.executeQuery("SELECT DISTINCT id FROM switch_events"); 
            while(result.next()){
                switches.add(result.getInt("id"));
            }
 

            long lastevent = 0;
            int val,id;
            int i = 0;
            EventList eventlist = new EventList(false); 
            long time;
            long start = System.currentTimeMillis();
            String type;
            KeyList keylist;
            on = new HashMap<KeyList, Float>();
            off = new HashMap<KeyList, Float>();
            count = new HashMap<KeyList, Integer>();
            HashMap<KeyList,Integer> denominator = new HashMap<KeyList,Integer>();
            System.out.println("fetching data from db");
            result = stmt.executeQuery("(SELECT id, timestamp, 'sensor' AS type, '0' AS status FROM sensor_events) UNION " +
            		"(SELECT id, timestamp,'switch' AS type, status FROM switch_events) ORDER BY timestamp;"); 
            System.out.println(" iterating resultset");
            while(result.next()){
                i++;
                id = result.getInt("id");
                time = result.getTimestamp("timestamp").getTime();
                type = result.getString("type");
                //System.out.println("event : "+id+" type: "+type+" time : "+time);
                if(type.equals("sensor")){
                    eventlist.add(new SensorEvent(id, time));
                    keylist = new KeyList(eventlist);
                    if (denominator.containsKey(keylist)){
                        denominator.put(keylist, denominator.get(keylist) + 1);
                    } else{
                        denominator.put(keylist, 1);
                    }       
                    lastevent = time;
                }
                else if(type.equals("switch")){
                    temp = (result.getBoolean("status")) ? on : off;

                    if(time > lastevent + Config.patternInterval){
                        eventlist = new EventList(false);
                        keylist = new KeyList(eventlist);
                        if (denominator.containsKey(keylist)){
                            denominator.put(keylist,denominator.get(keylist)+1);
                        }else{
                            denominator.put(keylist, 1);
                        }
                    }
                    keylist = new KeyList(eventlist);
                    keylist.add(id);

                    if(temp.containsKey(keylist)){
                        temp.put(keylist,temp.get(keylist)+1);
                    }
                    else{
                        temp.put(keylist,1f);
                    }
                 
                }
            } 
            KeyList ksub;
            long end = System.currentTimeMillis();
            long runtime = end-start;
            System.out.println("rows : "+i);
            System.out.println("runtime = "+runtime);
            for(KeyList k : on.keySet()){
                ksub = k.subList(0,k.size()-2);
                on.put(k,on.get(k) / denominator.get(ksub));
                count.put(ksub, denominator.get(ksub));
            }
            for(KeyList k : off.keySet()){
                ksub = k.subList(0,k.size()-2);
                off.put(k, off.get(k) / denominator.get(ksub));
                count.put(ksub, denominator.get(ksub));
            }
            System.out.printf("basic %d/%d (%d)\n", on.size(), off.size(), denominator.size());
            
        } catch (SQLException se){
            System.out.println("SQLException: " + se.getMessage());
            System.out.println("SQLState: " + se.getSQLState());
            System.out.println("VendorError: " + se.getErrorCode());
 
        }
    }

    public void generateZoneMatrices(){
        System.out.println("generating zone matrices");

		HashMap<KeyList,Float> temp,zoneOn,zoneOff;
		zoneOn = new HashMap<KeyList,Float>();
		zoneOff = new HashMap<KeyList,Float>();
		long lastevent = 0;
		int val,id;
		int i = 0;
		EventList eventlist = new EventList(true); 
		long time;
		long start = System.currentTimeMillis();
		String type;
		KeyList keylist;
		HashMap<KeyList,Integer> denominator = new HashMap<KeyList,Integer>();   
        try {
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
                    lastevent = time;
                    if (!eventlist.containsZoneEvent())
                        continue;

                    keylist = new KeyList(eventlist);
                    if (denominator.containsKey(keylist)) {
                        denominator.put(keylist,denominator.get(keylist)+1);
                    } else{
                        denominator.put(keylist,1);
                    }       
                }
                else if (type.equals("switch")) {
                   temp = (result.getBoolean("status")) ? zoneOn : zoneOff; 
//                   if(time > lastevent+Config.patternInterval){
//                        eventlist = new EventList(true);
//                        keylist = new KeyList(eventlist);
//                        if (denominator.containsKey(keylist)){
//                            denominator.put(keylist,denominator.get(keylist)+1);
//                        } else {
//                            denominator.put(keylist,1);
//                        }
//                    }
					if(eventlist.containsZoneEvent()){
						keylist = new KeyList(eventlist);
						keylist.add(id);
//						System.out.println("keylist : "+keylist.toString());
						if(temp.containsKey(keylist)){
							temp.put(keylist,temp.get(keylist)+1);
						}
						else{
							temp.put(keylist,1f);
						}
					}
                }
            } 
            KeyList ksub;
            long end = System.currentTimeMillis();
            long runtime = end-start;
            System.out.println("rows : "+i);
            System.out.println("runtime = "+runtime);
            for(KeyList k : zoneOn.keySet()){
                ksub = k.subList(0,k.size()-2);
                zoneOn.put(k,zoneOn.get(k)/denominator.get(ksub));
                count.put(ksub, denominator.get(ksub));
            }
             for(KeyList k : zoneOff.keySet()){
                ksub = k.subList(0,k.size()-2);
                zoneOff.put(k,zoneOff.get(k)/denominator.get(ksub));
                count.put(ksub, denominator.get(ksub));
            }
            
        }
        catch (SQLException se){
            System.out.println("SQLException: " + se.getMessage());
            System.out.println("SQLState: " + se.getSQLState());
            System.out.println("VendorError: " + se.getErrorCode());
 
        }
		for(KeyList k: zoneOn.keySet()){
			on.put(k,zoneOn.get(k));
		}
		for(KeyList k: zoneOff.keySet()){
			off.put(k,zoneOff.get(k));
		}
        System.out.printf("zone %d/%d (%d)\n", on.size(), off.size(), denominator.size());
    }
    public  void printMatrices(){
        KeyList ksub;
        System.out.println();
		System.out.println("*********************************************"); 
		System.out.println("printing matrix on");
		System.out.println("*********************************************"); 
		for(KeyList k : on.keySet()){
            ksub = k.subList(0,k.size()-2);
            System.out.print("count: " + count.get(ksub) + " ");

            System.out.print("key: ");
			k.printValues();
			
			System.out.println("value: "+on.get(k));
		}
		System.out.println();
		System.out.println();
		System.out.println("*********************************************"); 
		System.out.println("printing matrix off");
		System.out.println("*********************************************"); 

		for(KeyList k : off.keySet()){
            ksub = k.subList(0,k.size()-2);
            System.out.print("count: " + count.get(ksub) + " ");

            System.out.print("key: ");
			k.printValues();
			
			System.out.println("value: "+off.get(k)); 
		}
		System.out.println();        
    } 
   
}
