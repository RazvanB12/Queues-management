import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MainView extends JFrame {
    private JPanel panel;
    private JLabel title;
    private JLabel noClients, noQueues, simTime, minArrTime, maxArrTime, minSerTime, maxSerTime;
    private JTextField noClientsText, noQueuesText, simTimeText, minArrTimeText, maxArrTimeText, minSerTimeText, maxSerTimeText;
    private JButton generateButton;

    public MainView() {
        panel = new JPanel();
        panel.setLayout(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500, 550);

        title = new JLabel("QUEUES MANAGEMENT SIMULATOR");
        title.setBounds(70, 30, 400, 25);
        title.setFont(new Font("Tahoma", Font.BOLD, 20));

        noClients = new JLabel("Number of clients:");
        noClients.setBounds(110, 80, 290, 25);
        noClients.setFont(new Font("Tahoma", Font.BOLD, 14));

        noQueues = new JLabel("Number of queues:");
        noQueues.setBounds(110, 130, 290, 25);
        noQueues.setFont(new Font("Tahoma", Font.BOLD, 14));

        simTime = new JLabel("Simulation time:");
        simTime.setBounds(110, 180, 290, 25);
        simTime.setFont(new Font("Tahoma", Font.BOLD, 14));

        minArrTime = new JLabel("Minimum arrival time:");
        minArrTime.setBounds(110, 230, 290, 25);
        minArrTime.setFont(new Font("Tahoma", Font.BOLD, 14));

        maxArrTime = new JLabel("Maximum arrival time:");
        maxArrTime.setBounds(110, 280, 290, 25);
        maxArrTime.setFont(new Font("Tahoma", Font.BOLD, 14));

        minSerTime = new JLabel("Minimum service time:");
        minSerTime.setBounds(110, 330, 290, 25);
        minSerTime.setFont(new Font("Tahoma", Font.BOLD, 14));

        maxSerTime = new JLabel("Maximum service time:");
        maxSerTime.setBounds(110, 380, 290, 25);
        maxSerTime.setFont(new Font("Tahoma", Font.BOLD, 14));

        noClientsText = new JTextField();
        noClientsText.setBounds(290, 80, 100, 25);

        noQueuesText = new JTextField();
        noQueuesText.setBounds(290, 130, 100, 25);

        simTimeText = new JTextField();
        simTimeText.setBounds(290, 180, 100, 25);

        minArrTimeText = new JTextField();
        minArrTimeText.setBounds(290, 230, 100, 25);

        maxArrTimeText = new JTextField();
        maxArrTimeText.setBounds(290, 280, 100, 25);

        minSerTimeText = new JTextField();
        minSerTimeText.setBounds(290, 330, 100, 25);

        maxSerTimeText = new JTextField();
        maxSerTimeText.setBounds(290, 380, 100, 25);

        generateButton = new JButton("GENERATE QUEUES");
        generateButton.setBounds(150, 430, 200, 50);
        generateButton.setFont(new Font("Tahoma", Font.BOLD, 14));

        panel.add(title);
        panel.add(noClients);
        panel.add(noQueues);
        panel.add(simTime);
        panel.add(minArrTime);
        panel.add(maxArrTime);
        panel.add(minSerTime);
        panel.add(maxSerTime);
        panel.add(noClientsText);
        panel.add(noQueuesText);
        panel.add(simTimeText);
        panel.add(minArrTimeText);
        panel.add(maxArrTimeText);
        panel.add(minSerTimeText);
        panel.add(maxSerTimeText);
        panel.add(generateButton);

        this.add(panel);
    }

    public void addButtonListener(ActionListener listener) {
        generateButton.addActionListener(listener);
    }

    public JTextField getNoClientsText() {
        return noClientsText;
    }

    public JTextField getNoQueuesText() {
        return noQueuesText;
    }

    public JTextField getSimTimeText() {
        return simTimeText;
    }

    public JTextField getMinArrTimeText() {
        return minArrTimeText;
    }

    public JTextField getMaxArrTimeText() {
        return maxArrTimeText;
    }

    public JTextField getMinSerTimeText() {
        return minSerTimeText;
    }

    public JTextField getMaxSerTimeText() {
        return maxSerTimeText;
    }
}
