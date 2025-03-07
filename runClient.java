
import javax.swing.JFrame;

public class runClient {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        ClientGUI guiObjects = new ClientGUI();
        guiObjects.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        guiObjects.setSize(850, 600);
        guiObjects.setVisible(true);
        guiObjects.setResizable(true);

        Client client = new Client();
        client.connectToServer();
        client.getStreams();
        client.communicate();
    }

}
