
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;
import za.ac.cput.domain.Car;

/**
 *
 * @author 222369345 Ruth Ifeoma Onwutali
 */
public class Client {

    protected static Object obj;
    private Socket client;
    protected static ObjectOutputStream out;
    protected static ObjectInputStream in;

    public void connectToServer() {
        // Attempt to establish connection to server
        try {
            client = new Socket("127.0.0.1", 6666);
            getStreams();
        } catch (IOException ioe) {
            System.out.println("IOException: " + ioe.getMessage());
        }

    }//end listenForClients

    //write data to the server    
    public void sendData(String myMsg) {
        try {
            out.writeObject(myMsg);
            out.flush();
        } catch (IOException ioe) {
            System.out.println("IO Exception in sendData(myMsg): " + ioe.getMessage());
        }
    }//end sendData*/

    //setup the communication streams    
    public void getStreams() {
        try {
            out = new ObjectOutputStream(client.getOutputStream());
            out.flush();
            in = new ObjectInputStream(client.getInputStream());
        } catch (IOException ioe) {
            System.out.println("IO Exception: " + ioe.getMessage());
        }
    }//end getStreams

    //to handle String from the server
    public void handleSServerRsp(String serverRsp) {
        if (serverRsp == null) {
            System.out.println("Server response is null");
            return; //if the string is not empty it is passed to the JTextArea method
        }
        ClientGUI.taServer.append("From Server: " + serverRsp + "\n");
    }//handleServerRsp

    //to handleArrayList for the server
    public void handleAServerRsp(ArrayList<Car> carList) {
        if (carList.isEmpty()) {
            System.out.println("Empty vehicle list received from server.");
            return; //if the arraylist is not empty it is passed to the populateComboBox method
        }
        populateComboBox(carList);

    }//endhandleServerRsp

    //to add arraylsit from server to the JComboBox
    public void populateComboBox(ArrayList<Car> vehicleList) {
        DefaultComboBoxModel cbomodel = new DefaultComboBoxModel();
        for (Object fleet : vehicleList) {
            Car car = (Car) fleet;
            cbomodel.addElement(car.getCarName());
        }
        ClientGUI.cboVehicle.setModel(cbomodel);

    }//end populateComboBox

    // To read Objects from the server.
    public void readServerObjects(Object serObj) {
        try {
            obj = in.readObject();
        } catch (IOException e) {
            System.out.println("IOException in readServerObjects()" + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("CNFException in readServerObjects()" + e.getMessage());
        }
    }//end readServerObjects

    //to catch the Arraylist for the JTable
    public void populateJTableList() {
       
        if (obj instanceof ArrayList) {
            populateJTable((ArrayList<Car>) obj);
        } else {
            System.out.println("Unknown message type received from server.");
        }
    }

    //to populate JTable
    public void populateJTable(ArrayList<Car> carList) {
        DefaultTableModel JtModel = new DefaultTableModel();
        JtModel.addColumn("Car Id");
        JtModel.addColumn("Car Name");
        JtModel.addColumn("Car Vote");

        // Add data to the table model
        for (Object fleet : carList) {
            Car car = (Car) fleet;
            JtModel.addRow(new Object[]{car.getCarId(), car.getCarName(), car.getCarVote()});
        }
        ClientGUI.tblVotes.setModel(JtModel);
    }//end populateJTable

//to display the objects from the client in their places
    public void communicate() {
        do {
            readServerObjects(obj);
            if (obj instanceof String) {
                handleSServerRsp((String) obj);
            } else if (obj instanceof ArrayList) {
                handleAServerRsp((ArrayList<Car>) obj);
            } else {
                System.out.println("Unknown message type received from server.");
            }
        } while (!obj.equals("Terminate"));

        closeConnection();
    }//end Communicate

    //to close connections
    public void closeConnection() {
        try {
            out.close();
            in.close();
            client.close();
        } catch (IOException ioe) {
            System.out.println("IO Exception: " + ioe.getMessage());
        }
    }//closeConnection

}//end class

