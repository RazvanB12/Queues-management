import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller {
    private MainView mainView;

    public Controller(MainView mainView) {
        this.mainView = mainView;
        mainView.setVisible(true);
        mainView.addButtonListener(new ButtonListener());
    }

    class ButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int noQueues = Integer.parseInt(mainView.getNoQueuesText().getText());
            int noClients = Integer.parseInt(mainView.getNoClientsText().getText());
            int simulationTime = Integer.parseInt(mainView.getSimTimeText().getText());
            int minArrTime = Integer.parseInt(mainView.getMinArrTimeText().getText());
            int maxArrTime = Integer.parseInt(mainView.getMaxArrTimeText().getText());
            int minSerTime = Integer.parseInt(mainView.getMinSerTimeText().getText());
            int maxSerTime = Integer.parseInt(mainView.getMaxSerTimeText().getText());
            Generator Generator = new Generator(noQueues, simulationTime, noClients, minArrTime, maxArrTime, minSerTime, maxSerTime);
            Generator.clientsGenerator();
            Generator.queuesGenerator();
            Generator.start();
            Controller.this.mainView.setVisible(false);
        }
    }

}
