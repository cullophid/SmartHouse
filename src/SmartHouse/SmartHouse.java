package SmartHouse;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
public class SmartHouse{
    Connection conn = null;
    Statement stmt;
    /*
     * Constructor for the class SmartHouse
     * Handles the input and output for the ai
     */
    public SmartHouse(){
                try {
            Class.forName("com.mysql.jdbc.Driver");//load the mysql driver
            conn = DriverManager.getConnection("jdbc:mysql://localhost/kiiib?user=KIIIB&password=42");//connect to the database
            stmt = conn.createStatement();
            MarkovTable mt = new MarkovTable();
            mt.generateMarkovTable();
        }
        catch (SQLException se){
            System.out.println("SQLException: " + se.getMessage());
            System.out.println("SQLState: " + se.getSQLState());
            System.out.println("VendorError: " + se.getErrorCode());
 
        }
        catch (Exception e){
             e.printStackTrace();
       }
        System.out.println("HEllo SCALA!!");
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
}
