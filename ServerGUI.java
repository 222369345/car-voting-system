
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;


/**
 *
 * @author 27604
 */
public class ServerGUI extends JFrame implements ActionListener {
     //JPanels
    private JPanel pnlTop = new JPanel();
    private JPanel pnlCenter = new JPanel();
    private JPanel pnlButton = new JPanel();
    
    //JButton
    private JButton btnExit = new JButton("Exit");
    private JButton btnSend = new JButton("Send");
    
    //JFields
    private JLabel lblClient = new JLabel("Text From Client: ");
    protected static JTextArea taClient = new JTextArea(5,40);
    private JLabel lblServer = new JLabel("Send Text To Client: ");
    protected static JTextField txtServer = new JTextField(20);
    
    
    public ServerGUI(){
        super("Server");
        
        pnlTop.setLayout(new GridLayout(2, 1, 5, 5));
        pnlTop.add(lblClient);
        pnlTop.add(taClient);
        
        pnlCenter.setLayout(new GridLayout(4,4,5,5));
        pnlCenter.add(lblServer);
        pnlCenter.add(txtServer);
        //pnlCenter.add(btnSend);
        //pnlCenter.add(btnExit);
        
        pnlButton.setLayout(new GridLayout(1, 2, 5, 5));
        pnlButton.add(btnSend);
        pnlButton.add(btnExit);
        
       this.setLayout(new BorderLayout());
       this.add(pnlTop, BorderLayout.NORTH);
       this.add(pnlCenter, BorderLayout.CENTER);
       this.add(pnlButton, BorderLayout.SOUTH);
        
        btnExit.addActionListener(this);
        //btnSend.addActionListener(this);
        taClient.setEditable(false);
    }//end constructor
    
     @Override
    public void actionPerformed(ActionEvent e) {
        Server sr = new Server();
        if(e.getSource()==btnSend){
            String rsp = txtServer.getText();
            sr.sendData(rsp);
            txtServer.setText("");
            
        }
        if(e.getSource()==btnExit){
            System.exit(0);
        }
       
    }//end actionPerformed
}
