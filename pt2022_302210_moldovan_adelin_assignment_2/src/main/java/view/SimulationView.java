package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;


public class SimulationView extends JFrame {

    private final JTextField textFieldNrClients;
    private final JTextField textFieldNumberofqueues;
    private final JTextField textFieldTimeLmit;
    private final JTextField textFieldMinArrivalTime;
    private final JTextField textFieldMaxArrivalTime;
    private final JTextField textFieldMinProcTime;
    private final JTextField textFieldMaxProcTime;
    public static JTextArea simulationTextArea = new JTextArea();
    JButton btnStartSimulation = new JButton("Start simulation");

    public SimulationView() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 890, 422);
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("Number of clients");
        lblNewLabel.setBounds(10, 24, 94, 23);
        contentPane.add(lblNewLabel);

        JLabel lblNumberofqueues = new JLabel("Number of queues");
        lblNumberofqueues.setBounds(10, 57, 94, 23);
        contentPane.add(lblNumberofqueues);

        JLabel lblTimeLimit = new JLabel("Time Limit");
        lblTimeLimit.setBounds(10, 90, 94, 23);
        contentPane.add(lblTimeLimit);

        JLabel lblMaximumArrivalTime = new JLabel("Minimum arrival time");
        lblMaximumArrivalTime.setBounds(10, 123, 108, 23);
        contentPane.add(lblMaximumArrivalTime);

        JLabel lblMaximumArrivalTime_1 = new JLabel("Maximum arrival time");
        lblMaximumArrivalTime_1.setBounds(10, 160, 108, 23);
        contentPane.add(lblMaximumArrivalTime_1);

        JLabel lblMinimumProcessingTime = new JLabel("Minimum processing time");
        lblMinimumProcessingTime.setBounds(10, 196, 132, 23);
        contentPane.add(lblMinimumProcessingTime);

        JLabel lblMaximumProcessingTime = new JLabel("Maximum processing time");
        lblMaximumProcessingTime.setBounds(10, 237, 132, 23);
        contentPane.add(lblMaximumProcessingTime);

        textFieldNrClients = new JTextField();
        textFieldNrClients.setBounds(178, 26, 96, 19);
        contentPane.add(textFieldNrClients);
        textFieldNrClients.setColumns(10);

        textFieldNumberofqueues = new JTextField();
        textFieldNumberofqueues.setColumns(10);
        textFieldNumberofqueues.setBounds(178, 59, 96, 19);
        contentPane.add(textFieldNumberofqueues);

        textFieldTimeLmit = new JTextField();
        textFieldTimeLmit.setColumns(10);
        textFieldTimeLmit.setBounds(178, 92, 96, 19);
        contentPane.add(textFieldTimeLmit);

        textFieldMinArrivalTime = new JTextField();
        textFieldMinArrivalTime.setColumns(10);
        textFieldMinArrivalTime.setBounds(178, 125, 96, 19);
        contentPane.add(textFieldMinArrivalTime);

        textFieldMaxArrivalTime = new JTextField();
        textFieldMaxArrivalTime.setColumns(10);
        textFieldMaxArrivalTime.setBounds(178, 162, 96, 19);
        contentPane.add(textFieldMaxArrivalTime);

        textFieldMinProcTime = new JTextField();
        textFieldMinProcTime.setColumns(10);
        textFieldMinProcTime.setBounds(178, 198, 96, 19);
        contentPane.add(textFieldMinProcTime);

        textFieldMaxProcTime = new JTextField();
        textFieldMaxProcTime.setColumns(10);
        textFieldMaxProcTime.setBounds(178, 239, 96, 19);
        contentPane.add(textFieldMaxProcTime);

        btnStartSimulation.setBounds(109, 293, 108, 21);
        contentPane.add(btnStartSimulation);

        simulationTextArea.setBounds(362,24,504,339);

        JScrollPane scrollPane=new JScrollPane();
        scrollPane.setBounds(362,24,504,339);
        contentPane.add(scrollPane);
        scrollPane.setViewportView(simulationTextArea);

    }


    public  int getNrClients(){
        return Integer.parseInt(textFieldNrClients.getText());
    }

    public  int getNrOfQueues(){
        return Integer.parseInt(textFieldNumberofqueues.getText());
    }

    public int getTimeLimit(){
        return Integer.parseInt(textFieldTimeLmit.getText());
    }

    public  int getMinATime(){
        return Integer.parseInt(textFieldMinArrivalTime.getText());
    }

    public  int getMaxATime(){
        return Integer.parseInt(textFieldMaxArrivalTime.getText());
    }

    public  int getMinPTime() {
        return Integer.parseInt(textFieldMinProcTime.getText());
    }

    public  int getMaxPTime(){
        return Integer.parseInt(textFieldMaxProcTime.getText());
    }

    public static void setSimulationTextArea(String s){
        simulationTextArea.insert(s,0);
    }

    public void addSimulationListener(ActionListener listenForSimulate){
        btnStartSimulation.addActionListener(listenForSimulate);
    }
}