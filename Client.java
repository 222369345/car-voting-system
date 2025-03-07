
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import za.ac.cput.domain.Car;

/**
 *
 * @author 27604
 */
public class Client {

    private String serverRsp;
    private Socket client;
    protected static ObjectOutputStream out;
    protected static ObjectInputStream in;

    public void connectToServer() {

        // Attempt to establish connection to server
        try {
            // Create socket
            client = new Socket("127.0.0.1", 6666);
            System.out.println("Connected!");
        } catch (IOException ioe) {
            System.out.println("IOException: " + ioe.getMessage());
        }

    }//end listenForClients

    public void getStreams() {
        try {
            out = new ObjectOutputStream(client.getOutputStream());
            out.flush();
            in = new ObjectInputStream(client.getInputStream());
        } catch (IOException ioe) {
            System.out.println("IO Exception: " + ioe.getMessage());
        }

    }//end getStreams

    public void closeConnection() {
        try {
            out.close();
            in.close();
            client.close();

        } catch (IOException ioe) {
            System.out.println("IO Exception: " + ioe.getMessage());
        }
    }//end closeConnection
    
    public void sendData(String rsp){
        try{
            out.writeObject(rsp);
            out.flush();
        }catch(IOException e){
            System.out.println("IOException in sendData(): " + e.getMessage());
        }
    }//end sendData
    
    public void receiveData(){
       try{
           serverRsp = (String) in.readObject();
           GUI.taServer.append("From Server: " + serverRsp + "\n");
       }catch(IOException e){
           System.out.println("IOException in receiveData: " + e.getMessage());
       }catch(ClassNotFoundException e){
           System.out.println("CNFException in receiveData: " + e.getMessage());
       }
    }//receiveData
}//end class


