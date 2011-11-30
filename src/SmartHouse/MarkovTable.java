package SmartHouse;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;

public class MarkovTable{
    Statement stmt;
    Connection conn;
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

    } 
}

