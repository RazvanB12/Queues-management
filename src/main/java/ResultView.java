import javax.swing.*;
import java.awt.*;

public class ResultView extends JFrame {
    private JPanel panel;
    private JLabel personsField;
    private JLabel timerField;
    private JTextField[] queuesText;
    private JLabel awtField;
    private JLabel astField;
    private JLabel phField;

    public ResultView(int noPersons, int noQueues, int seconds, int[] sizes) {
        panel = new JPanel();
        panel.setLayout(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(370, 265 + 25 * noQueues);

        personsField = new JLabel();
        timerField = new JLabel();
        queuesText = new JTextField[noQueues];
        awtField = new JLabel();
        astField = new JLabel();
        phField = new JLabel();

        personsField.setText("NUMBER OF WAITING PERSONS: " + Integer.toString(noPersons));
        personsField.setBounds(30, 30, 300, 25);
        personsField.setFont(new Font("Tahoma", Font.BOLD, 14));
        personsField.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(personsField);

        timerField.setText("TIMER: " + Integer.toString(seconds));
        timerField.setBounds(30, 55, 300, 25);
        timerField.setFont(new Font("Tahoma", Font.BOLD, 14));
        timerField.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(timerField);

        awtField.setText("AVERAGE WAITING TIME: 0");
        awtField.setBounds(30, 80, 300, 25);
        awtField.setFont(new Font("Tahoma", Font.BOLD, 14));
        awtField.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(awtField);

        astField.setText("AVERAGE SERVING TIME: 0");
        astField.setBounds(30, 105, 300, 25);
        astField.setFont(new Font("Tahoma", Font.BOLD, 14));
        astField.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(astField);

        phField.setText("PEAK HOUR: 0");
        phField.setBounds(30, 130, 300, 25);
        phField.setFont(new Font("Tahoma", Font.BOLD, 14));
        phField.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(phField);

        for (int i = 0; i < noQueues; i++) {
            queuesText[i] = new JTextField();
            queuesText[i].setBounds(120, 185 + 25 * i, 110, 25);
            queuesText[i].setText("QUEUE" + Integer.toString(i + 1) + " -- " + sizes[i]);
            queuesText[i].setHorizontalAlignment(SwingConstants.CENTER);
            queuesText[i].setEditable(false);
            panel.add(queuesText[i]);
        }
        this.add(panel);
        this.setVisible(true);
    }

    public void update(int noPersons, int noQueues, int seconds, int[] sizes, float awt, float ast, int ph) {
        personsField.setText("NUMBER OF WAITING PERSONS: " + Integer.toString(noPersons));
        timerField.setText("TIMER: " + Integer.toString(seconds));
        awtField.setText("AVERAGE WAITING TIME: " + Float.toString(awt));
        astField.setText("AVERAGE SERVING TIME: " + Float.toString(ast));
        phField.setText("PEAK HOUR: " + Integer.toString(ph));
        for (int i = 0; i < noQueues; i++) {
            queuesText[i].setText("QUEUE" + Integer.toString(i + 1) + " -- " + sizes[i]);
            queuesText[i].setHorizontalAlignment(SwingConstants.CENTER);
        }
    }
}
