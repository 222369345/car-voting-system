package za.ac.cput.dao;

import za.ac.cput.connection.DbConnection;
import za.ac.cput.domain.Car;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author 27604
 */
public class DAO {

    private Connection con;
    private PreparedStatement pstmt;

    //to connect to the database
    public DAO() {
        try {
            this.con = DbConnection.derbyConnection();
        } catch (Exception ioe) {
            JOptionPane.showMessageDialog(null, "Exception in DAO(): " + ioe.getMessage());
        }
    }//end DAO()

    //to close the connections 
    public void closeConnections() {
        try {
            if (pstmt != null) {
                pstmt.close();
            }
            if (con != null) {
                con.close();
            }
        } catch (SQLException exception) {
            JOptionPane.showMessageDialog(null, "Error closeConnection(): " + exception.getMessage());
        }
    }//end close connection()

    public void createSubjectTable() {
        int ok;
        try {
            pstmt = con.prepareStatement("create table Car (id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY ,name VARCHAR(30), vote INT, PRIMARY KEY(id))");
            ok = pstmt.executeUpdate();

        } catch (SQLException sqlE) {
            JOptionPane.showMessageDialog(null, "SQL Error: " + sqlE.getMessage());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if(con!=null){
                    con.close();
                }
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(null, exception.getMessage(), "Warning", JOptionPane.ERROR_MESSAGE);
            }
        }//end finally

    }//end create method  

    //to save the vehicles added by the client to the database
    public void saveVehicles(Car car) {
        int ok;
        try {
            pstmt = con.prepareStatement("INSERT into Car (name) VALUES(?)",Statement.RETURN_GENERATED_KEYS);
            
            pstmt.setString(1, car.getCarName());

            System.out.println("sql: ");
            ok = pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            if(rs.next()){
                int id = rs.getInt(1);
                System.out.println("Car Added: " + id);
               // car.setCarId(rs.getInt(1));
            }if(rs!=null){
                rs.close();
            }

        } catch (SQLException sqlE) {
            JOptionPane.showMessageDialog(null, "SQL Error: " + sqlE.getMessage());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        } finally {
            try {
                if (pstmt == null) {
                    closeConnections();
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Warning", JOptionPane.ERROR_MESSAGE);
            }
        }//end finally

    }//end saveVehicles

    //to populate the JComboBox on the client side
    public void populateComboBox(ArrayList carList) {

        try {
            pstmt = con.prepareStatement("SELECT name FROM car");
            ResultSet rs = null;
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Car car = new Car(rs.getString("name"));
                carList.add(car);
                System.out.println("Read Car Names: " + car);
            }
            if (pstmt == null) {
                closeConnections();
            }
        } catch (SQLException e) {
            System.err.println("Error Reading from Database Table: " + e.getMessage());
        }
    }//end populateComboBox

    //To save the votes onto the database
    public void saveVotes(Car c) {
        int ok;
        try {
            pstmt = con.prepareStatement("UPDATE Car SET vote = vote + 1 where name = ?");
            pstmt.setString(1, c.getCarName());

            ok = pstmt.executeUpdate();
            if (ok == 0) {
                System.out.println("Car not found");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "SQL Error: " + e.getMessage());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        } finally {
            try {
                if (pstmt == null) {
                    closeConnections();
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Exception in saveVotes(): " + e.getMessage());
            }
        }//end finally

    }//end savecar

    //to send the votes to the client 
    public void sendVotes(ArrayList voteList) {
        ResultSet rs = null;
        try {
            pstmt = con.prepareStatement("SELECT * FROM Car");
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Car vehicle = new Car(rs.getInt("id"), rs.getString("name"), (rs.getInt("vote")));
                voteList.add(vehicle);
                System.out.println("Send Votes: " + vehicle);
            }if(rs!= null){
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Exception in sendVotes: " + e.getMessage());
        }
    }//sen1dVotes

}//end class

