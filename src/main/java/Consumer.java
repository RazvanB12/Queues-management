import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Consumer extends Thread {
    private BlockingQueue<Client> queue;
    private int totalWaitingTime;
    private boolean isOpened;
    private boolean empty;

    public Consumer() {
        this.queue = new LinkedBlockingQueue<Client>();
        totalWaitingTime = 0;
        isOpened = true;
        empty = true;
    }

    public void updateWaitingTime() {
        totalWaitingTime = 0;
        for (Client client : queue) {
            totalWaitingTime = totalWaitingTime + client.getServiceTime();
        }
    }

    public void joinQueue(Client newClient) {
        queue.add(newClient);
        updateWaitingTime();
    }

    @Override
    public void run() {
        while (isOpened == true) {
            if (queue.size() > 0 && empty == true) {
                Client client = queue.element();
                if (client.getServiceTime() == 1) {
                    queue.remove(client);
                    updateWaitingTime();
                }
                empty = false;
            } else if (queue.size() > 0) {
                Client client = queue.element();
                if (client.getServiceTime() == 1) {
                    queue.remove(client);
                    updateWaitingTime();
                } else {
                    client.setServiceTime(client.getServiceTime() - 1);
                    updateWaitingTime();
                }
            }

            try {
                sleep(1000);
                //sleep(1);
            } catch (InterruptedException e) {
                e.getStackTrace();
            }
        }
    }

    public BlockingQueue getQueue() {
        return queue;
    }

    public int getTotalWaitingTime() {
        return totalWaitingTime;
    }

    public void setOpened(boolean opened) {
        isOpened = opened;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }
}
