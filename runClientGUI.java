
import javax.swing.JFrame;

/**
 *
 * @author 27604
 */
public class runClientGUI {
    
     public static void main(String[] args) {
     GUI guiObjects = new GUI();
        guiObjects.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        guiObjects.setSize(850,600);
        guiObjects.setVisible(true);
        guiObjects.setResizable(true);
        
        Client client = new Client();
       client.connectToServer();
       client.getStreams();
       client.receiveData();
     }
}
