package SmartHouse;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.HashMap;
public class MarkovTable{
    Statement stmt;
    Connection conn;
    HashMap<Integer,HashMap<Integer,Integer>> MarkovTable;
    public MarkovTable(){
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
    public void generateMarkovTable(){
        System.out.println("Markov away!!");
        try{
            // create a table of the correct dimensions
            MarkovTable = new HashMap<Integer,HashMap<Integer,Integer>>();
            HashMap<Integer,Integer> sensorEvents = new HashMap<Integer,Integer>();

            ResultSet result = stmt.executeQuery("SELECT DISTINCT id FROM sensor_events"); 
            while(result.next()){
                sensorEvents.put(result.getInt("id"),0);
                MarkovTable.put(result.getInt("id"),new HashMap<Integer,Integer>());
            }
            result = stmt.executeQuery("SELECT DISTINCT id FROM switch_events"); 
            while(result.next()){
                for(int sensor : sensorEvents.keySet()){
                    MarkovTable.get(sensor).put(result.getInt("id"),0);    
                } 
            }
            result = stmt.executeQuery("(select id,timestamp,'sensor' AS type from sensor_events) union (select id,timestamp,'switch' AS type from switch_events) order by timestamp;"); 
            int lastSensor = -1;
            while(result.next()){
                if(result.getString("type").equals("sensor")){
                    int id = result.getInt("id");
                    sensorEvents.put(id,sensorEvents.get(id)+1);
                    lastSensor = id;
                }else if(lastSensor != -1){
                    int id = result.getInt("id");
                    MarkovTable.get(lastSensor).put(id,MarkovTable.get(lastSensor).get(id)+1);
                    System.out.println("adding value");
                }
                
                
            }
            for(int sen : MarkovTable.keySet()){
                for(int swi : MarkovTable.get(sen).keySet()){
                    System.out.print(MarkovTable.get(sen).get(swi));

                }
                System.out.println();
            }

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
}

