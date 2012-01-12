package markov;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.HashMap;
/*
 * The class holding the markov table
 * this class is to be extended when the markovtable gets mopre dimensions
 */
public class MarkovTable {
    private Statement stmt;
    private Connection conn;
    private HashMap<Tuple,Integer> MarkovTable;// the HashMap holding the markov table
    private HashMap<Integer,Integer> sensorEvents;//this HashMap holds the number of times each sensor have fired
    private boolean status;
    /*
     * constructor for the markoc table class
     */
    public MarkovTable(boolean status){
        //connect to the database
        this.status = status;
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
    /*
     * generates the markovtable from the data stored in the database
     */
    public void generateMarkovTable(){
        try{
            MarkovTable = new HashMap<Tuple,Integer>();
            sensorEvents = new HashMap<Integer,Integer>();

            // initialize the HashMaps to zero
            ResultSet result = stmt.executeQuery("SELECT DISTINCT id FROM sensor_events"); 
            while(result.next()){
                sensorEvents.put(result.getInt("id"),0);
            }
            result = stmt.executeQuery("SELECT DISTINCT id FROM switch_events"); 
            while(result.next()){
                for(int sensor : sensorEvents.keySet()){
                    put(result.getInt("id"),sensor,0);
                } 
            }
            //first we fill in the times each sensor have been fired emidiately before a switch event 
            result = stmt.executeQuery("(select id,timestamp,'sensor' AS type, '0' AS status from sensor_events) union (select id,timestamp,'switch' AS type,status from switch_events) order by timestamp;"); 
            
            int lastSensor = -1;
            while(result.next()){
                if(result.getString("type").equals("sensor")){//count the times a sensor have fired
                    int id = result.getInt("id");
                    sensorEvents.put(id,sensorEvents.get(id)+1);
                    lastSensor = id;
                }else if(lastSensor != -1 && status == result.getBoolean("status")){
                    int id = result.getInt("id");
                    put(id,lastSensor,get(id,lastSensor)+1);
                }
               
                
            }
            // now we divide the number in the markov table with the total times a sensor have fired
            for (Tuple t : MarkovTable.keySet()){
                put(t,(get(t)*100)/sensorEvents.get(t.getY()));
                
                
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
    /*
     * Prints the markovtable to system.out
     */
    public void printMarkovTable(){
        if(status)
            System.out.println("printing ON table");
        else
            System.out.println("printing OFF table");
        int line = 0;
        for(Tuple t : MarkovTable.keySet()){
            line++;
            int i = MarkovTable.get(t);
            if(i<10)
                System.out.print(" |  ");
            else
                System.out.print(" | ");
            System.out.print(MarkovTable.get(t));
            if(line>=sensorEvents.keySet().size()){
                System.out.println(" | ");
                line=0;
            }
        }
    }
    /*
     * shorthand for adding data to the markovtable
     */
    public void put(int x,int y, int value){
        MarkovTable.put(new Tuple(x,y),value);
    }
    /*
     * shorthand for adding data to the markovtable
     */ 
    public void put(Tuple t, int value){
        MarkovTable.put(t,value);
    }
    /*
     * shorthand for retrieving data from the markovtable
     */
    private int get(int x , int y){
        return MarkovTable.get(new Tuple(x,y));
    }
    /*
     * shorthand for retrieving data from the markovtable
     */
    private int get(Tuple t){
        return MarkovTable.get(t);
    }

}
/*
 * Tuple class containing two values x and y used as index in the markovtable hashMap
 */
class Tuple{
    private int x,y;
    public Tuple(int x, int y){
        this.x = x;
        this.y = y;
    }
    public int hashCode(){
        return this.x^this.y; 
    }
    public boolean equals(Object o){
        if(((Tuple)o).getX() == this.x && ((Tuple)o).getY() == this.y)
           return true;
        else
           return false; 
    }
    public int getX(){return x;}
    public int getY(){return y;}
}
