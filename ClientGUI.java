
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.*;
import java.io.IOException;
import javax.swing.*;
import javax.swing.border.Border;
    import javax.swing.border.TitledBorder;
import za.ac.cput.domain.Car;

/**
 *
 * @author 27604
 */
public class ClientGUI extends JFrame  {

    //JPanels
    private JPanel pnlHeading = new JPanel();
    private JPanel pnlTop = new JPanel();
    private JPanel pnlText = new JPanel();
    private JPanel pnlJTable = new JPanel();
    private JPanel pnlCombo = new JPanel();
    private JPanel pnlButtons = new JPanel();
    private JPanel pnlWindows = new JPanel();
    private JPanel pnlLabels = new JPanel();

    //JLabels and JTextFields
    private JLabel lblHeading = new JLabel("Car of the Year");
    private JLabel lblCar = new JLabel("Select Car: ");
    protected static JComboBox cboVehicle = new JComboBox();
    private JLabel lblServer = new JLabel("From Server: ");
    protected static JTextArea taServer = new JTextArea(5, 20);

    //JButtons
    private JButton btnVote = new JButton("Vote");
    private JButton btnView = new JButton("View Votes");
    private JButton btnAdd = new JButton("Add Car");
    private JButton btnUpdate = new JButton("Update");
    private JButton btnExit = new JButton("Exit");

    protected static JTable tblVotes = new JTable();

    public ClientGUI() {

        super("Vote for the Car of the Year");

        //Creating a border around the JTable
        pnlJTable.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "STUDENT Table", TitledBorder.CENTER, TitledBorder.TOP));

        pnlHeading.setLayout(new GridLayout(1, 1, 5, 5));
        pnlHeading.add(lblHeading);

        //Customizing the lblHeading
        lblHeading.setForeground(Color.green);
        lblHeading.setFont(new Font("Arial", Font.PLAIN, 48));
        lblHeading.setHorizontalAlignment(JLabel.CENTER);
        Border border = BorderFactory.createLineBorder(Color.BLACK, 2);
        pnlHeading.setBorder(border);

        //JPanel layout 
        pnlWindows.setLayout(new GridLayout(2, 1, 5, 5));
        pnlTop.setLayout(new GridLayout(1, 3, 5, 5));

        //JLabels layout
        pnlLabels.setLayout(new GridLayout(2, 1, 5, 5));
        pnlLabels.add(lblCar);
        lblCar.setForeground(Color.green);
        lblCar.setFont(new Font("Arial", Font.PLAIN, 24));
        pnlLabels.add(lblServer);
        lblServer.setForeground(Color.green);
        lblServer.setFont(new Font("Arial", Font.PLAIN, 24));

        //JTextArea and JCo
        pnlText.setLayout(new GridLayout(2, 1, 5, 5));
        pnlText.add(cboVehicle);
        pnlText.add(taServer);

        //JTable layout
        pnlJTable.setLayout(new GridLayout(1, 1, 5, 5));

        //JButtons layout
        pnlButtons.setLayout(new GridLayout(1, 5, 5, 5));

        //Customizing the JButtons
        pnlButtons.add(btnVote);
        btnVote.setForeground(Color.green);
        btnVote.setBackground(Color.black);

        pnlButtons.add(btnView);
        btnView.setForeground(Color.green);
        btnView.setBackground(Color.black);

        pnlButtons.add(btnAdd);
        btnAdd.setForeground(Color.green);
        btnAdd.setBackground(Color.black);
        
        pnlButtons.add(btnUpdate);
        btnUpdate.setForeground(Color.green);
        btnUpdate.setBackground(Color.black);

        pnlButtons.add(btnExit);
        btnExit.setForeground(Color.green);
        btnExit.setBackground(Color.black);

        //adding panels to pnlTop
        pnlTop.add(pnlLabels);
        pnlTop.add(pnlText);
        pnlTop.add(pnlJTable);

        //adding scrollpane to the JTable and JTextArea
        pnlJTable.add(new JScrollPane(tblVotes));
        JScrollPane n = new JScrollPane();
        cboVehicle.add(n);

        //Layout of GUI
        this.setLayout(new BorderLayout());
        this.add(pnlHeading, BorderLayout.NORTH);
        this.add(pnlTop, BorderLayout.CENTER);
        this.add(pnlButtons, BorderLayout.SOUTH);

  
        btnVote.addActionListener(new ActionListener() {

            @Override

            public void actionPerformed(ActionEvent e) {
                /*sendData("Vote");
                String selectedCar = cboVehicle.getSelectedItem().toString();
                 Car car = new Car();
               car.setCarName(selectedCar);
                sendData(car);
                System.out.println("Vote Button clicked!");*/
                if (cboVehicle.getSelectedItem() != null) {
            String selectedCar = cboVehicle.getSelectedItem().toString();
            Car car = new Car();
            car.setCarName(selectedCar);
            sendData("Vote"); // Send vote command
            sendData(car); // Send Car object
            System.out.println("Vote Button clicked!");
        } else {
            JOptionPane.showMessageDialog(null, "Please select a vehicle.");
        }
            }
        });//end btnVote
        
        btnView.addActionListener(new ActionListener() {

            @Override

            public void actionPerformed(ActionEvent e) {
                Client cl = new Client();
                sendData("Results");
                cl.populateJTableList();
                

            }

        });//end btnView

        btnAdd.addActionListener(new ActionListener() {

            @Override

            public void actionPerformed(ActionEvent e) {
                String vote = "Add";
                sendData(vote);
                Car car = new Car();
                String carName = JOptionPane.showInputDialog("Enter Car Name: ");
                car.setCarName(carName);
                sendData(car);
            }
        });//end btnAdd    
        
         btnUpdate.addActionListener(new ActionListener() {

            @Override

            public void actionPerformed(ActionEvent e) {
                Client cl = new Client();
                sendData("Results");
                cl.populateJTableList();
                

            }

        });//end btnUpdate

        btnExit.addActionListener(new ActionListener() {

            @Override

            public void actionPerformed(ActionEvent e) {
                sendData("Terminate");
                System.exit(0);
            }
        });//end btnAdd  
         
    }//end constructor

    public void sendData(Object myMsg) {
        try {
            Client.out.writeObject(myMsg);
            Client.out.flush();
        } catch (IOException ioe) {
            System.out.println("IO Exception in sendData(myMsg): " + ioe.getMessage());
        }
    }//end sendData
}//end class
