
import javax.swing.JFrame;

/**
 *
 * @author 27604
 */
public class runServer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       
        ServerGUI guiObject = new ServerGUI();
        guiObject.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        guiObject.setSize(850, 600);
        guiObject.setResizable(true);
        guiObject.setVisible(true);
        
        Server server = new Server();
        server.listenForClients();
       //server.getStreams();
        server.processClient();
    }
    
}
