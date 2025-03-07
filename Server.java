
import za.ac.cput.dao.DAO;
import za.ac.cput.connection.DbConnection;
import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import za.ac.cput.domain.Car;

public class Server {

    private Connection con;
    private Statement stmt;
    private ServerSocket listener;
    private Socket client;
    private Object obj;
    private String clientRsp;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    // Constructor to connect to the database
    public Server() {
        try {
            this.con = DbConnection.derbyConnection();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Exception in Connecting with the database: " + e.getMessage());
        }
    }//END DBConnection

    // Close database connections
    public void closeDBConnections() {
        try {
            if (stmt != null) {
                stmt.close();
            }
            if (con != null) {
                con.close();
            }
        } catch (SQLException exception) {
            JOptionPane.showMessageDialog(null, "Error closeDBConnection(): " + exception.getMessage());
        }
    }//endcloseDbConnection

    // Initialize input and output streams
    public void getStreams() {
        try {
            out = new ObjectOutputStream(client.getOutputStream());
            in = new ObjectInputStream(client.getInputStream());
            out.flush(); // Flush the output stream
        } catch (IOException ioe) {
            JOptionPane.showMessageDialog(null, "IOException in getStreams(): " + ioe.getMessage());
        }
    }

    // Listen for client and accept connections
  // Listen for client connections and send initial data
    public void listenForClients() {
        try {
            listener = new ServerSocket(6666, 10);  // Server listens on port 6666
            System.out.println("Listening for Clients on Server...");
            client = listener.accept();  // Wait for a client to connect
            System.out.println("Client connected!");

            getStreams(); 

            sendCarList();  // Send car list to populate JComboBox on client side
        } catch (IOException ioe) {
            JOptionPane.showMessageDialog(null, "IOException in listenForClients(): " + ioe.getMessage());
        }
    }
    /*  public void listenForClients() {
        DAO dao = new DAO();
        try {
            listener = new ServerSocket(6666, 10);
            System.out.println("Listening for Clients on Server: ");
            client = listener.accept(); // Wait for a client to connect

            getStreams();
            
// Send the ArrayList to the client to populate the JcomboBox when client connects
            ArrayList<Car> carList = new ArrayList<>();
            dao.populateComboBox(carList);
            out.writeObject(carList);
            out.flush();
            System.out.println("ArrayList sent");
        } catch (IOException ioe) {
            JOptionPane.showMessageDialog(null, "IOException in listenForClient(): " + ioe.getMessage());
        }
    }//listenForClient
*/
 // Send the car list to the client for JComboBox
    private void sendCarList() {
        DAO dao = new DAO();
        ArrayList<Car> carList = new ArrayList<>();
        dao.populateComboBox(carList);  
        sendData(carList);  
        System.out.println("ArrayList sent to the client.");
    }
    // Close server connection
    public void closeSerConnection() {
        try {
            if (out != null) {
                out.close();
            }
            if (in != null) {
                in.close();
            }
            if (client != null) {
                client.close();
            }
        } catch (IOException ioe) {
            JOptionPane.showMessageDialog(null, "IOException in closeConnection(): " + ioe.getMessage());
        }
    }

    // Send data to the client
    public void sendData(Object obj) {
        try {
            out.writeObject(obj);
            out.flush();
        } catch (IOException ioe) {
            JOptionPane.showMessageDialog(null, "IOException in sendData(): " + ioe.getMessage());
        }
    }

    // Process client requests
    public void processClient() {
        DAO d = new DAO();
        Car c = new Car();
        do {
            try {
                obj = in.readObject(); // Read object from client and send arraylsit to populate the JTable
                if (obj instanceof String) {
                    clientRsp = (String) obj;
                    switch (clientRsp) {
                        case "Results":
                            ServerGUI.taClient.append("From Client: " + clientRsp + "\n");
                            ArrayList<Car> voteList = new ArrayList<>();
                            d.sendVotes(voteList);
                            sendData(voteList);
                          
                            break;

                        case "Add":
                            ServerGUI.taClient.append("From Client: " + clientRsp + "\n");
                            obj = in.readObject(); // Read Car object from client
                            c = (Car) obj;
                            d.saveVehicles(c);//save the car from client to database
                            System.out.println("Car Added");
                            sendData("Car Added");
                            break;

                        case "Vote":
                            ServerGUI.taClient.append("From Client: " + clientRsp + "\n");
                            obj = in.readObject(); // Read Car object from client
                            c = (Car) obj;
                            d.saveVotes(c);////save the vote from client to database
                            System.out.println("Vote Added");
                            sendData("Vote Added");
                            break;

                    }

                }
            } catch (IOException ioe) {
                JOptionPane.showMessageDialog(null, "IOException in processClient(): " + ioe.getMessage());
            } catch (ClassNotFoundException ioe) {
                JOptionPane.showMessageDialog(null, "CNFException in processClient(): " + ioe.getMessage());
            }
        } while (!obj.equals("Terminate"));

        closeSerConnection();

    }//end processClient
}//end class
