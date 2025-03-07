package za.ac.cput.connection;




import java.sql.*;
import javax.swing.JOptionPane;

/**
 *
 * @author 27604
 */
public class DbConnection {

    static Connection con;

    public static Connection derbyConnection() {
        try {
            String dbURL = ("jdbc:derby://localhost:1527/Car");
            String username = ("administration");
            String password = ("admin");
           

            System.out.println("About to get a connection....");
             con = DriverManager.getConnection(dbURL,username,password);
            System.out.println("Connection Established Successfully....");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "SQLException in Connection derby(): " + e.getMessage());
        }
        return con;
    }//end Connection()
}//end class
