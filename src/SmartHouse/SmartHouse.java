package SmartHouse;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;

public class SmartHouse{
    Connection conn = null;

    public SmartHouse(){
                try {
            Class.forName("com.mysql.jdbc.Driver");//load the mysql driver
            conn = DriverManager.getConnection("jdbc:mysql://localhost/kiiib?user=KIIIB&password=42");//connect to the database
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
    public void sensorEvent(int sensorId){
        try{
            System.out.println("Sensor "+sensorId+" fired!");        
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("INSERT INTO sensor_events VALUES("+sensorId+",NOW())");
        }
        catch(SQLException se){
            System.out.println("SQLException: " + se.getMessage());
            System.out.println("SQLState: " + se.getSQLState());
            System.out.println("VendorError: " + se.getErrorCode());
        }
    }
    public void switchEvent(int switchId, int status){
        try{
            System.out.println("Switch "+switchId+" turned "+status);
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("INSERT INTO switch_events VALUES("+switchId+","+status+",NOW())");

    
        }
        catch(SQLException se){
            System.out.println("SQLException: " + se.getMessage());
            System.out.println("SQLState: " + se.getSQLState());
            System.out.println("VendorError: " + se.getErrorCode());
        }
    }

}
