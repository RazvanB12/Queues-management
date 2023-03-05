import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.BlockingQueue;

public class Generator extends Thread {
    private int noQueues;
    private int noClients;
    private int simulationTime;
    private int minArrivalTime;
    private int maxArrivalTime;
    private int minServiceTime;
    private int maxServiceTime;
    private List<Client> clients;
    private List<Consumer> queues;
    private int runningTime;
    private boolean allClientsWereServed;
    private float averageWaitingTime;
    private float averageServingTime;
    private int peakHour;
    private int maxPeakHour;

    public Generator(int noQueues, int simulationTime, int noClients, int minArrivalTime, int maxArrivalTime, int minServiceTime, int maxServiceTime) {
        this.noQueues = noQueues;
        this.simulationTime = simulationTime;
        this.noClients = noClients;
        this.minArrivalTime = minArrivalTime;
        this.maxArrivalTime = maxArrivalTime;
        this.minServiceTime = minServiceTime;
        this.maxServiceTime = maxServiceTime;
        this.clients = new ArrayList<>();
        this.queues = new ArrayList<>();
        this.runningTime = 0;
        this.allClientsWereServed = false;
        averageWaitingTime = 0;
        averageServingTime = 0;
        peakHour = 0;
        maxPeakHour = 0;
    }

    public void clientsGenerator() {
        Random random = new Random();

        for (int i = 1; i <= noClients; i++) {
            int arrivalTime = random.nextInt(maxArrivalTime - minArrivalTime + 1) + minArrivalTime;
            int serviceTime = random.nextInt(maxServiceTime - minServiceTime + 1) + minServiceTime;
            Client client = new Client(i, arrivalTime, serviceTime);
            clients.add(client);
        }
        Collections.sort(clients);
    }

    public void queuesGenerator() {
        for (int i = 0; i < noQueues; i++) {
            queues.add(new Consumer());
        }
    }

    public boolean allQueuesAreEmpty() {
        for (int i = 0; i < noQueues; i++) {
            if (queues.get(i).getQueue().isEmpty() == false) {
                return false;
            }
        }
        return true;
    }

    public int getShortestQueue() {
        int min = queues.get(0).getTotalWaitingTime();
        int index = 0;

        for (int i = 1; i < noQueues; i++) {
            if (min > queues.get(i).getTotalWaitingTime()) {
                index = i;
                min = queues.get(i).getTotalWaitingTime();
            }
        }
        return index;
    }

    public void startQueues() {
        for (int i = 0; i < noQueues; i++) {
            queues.get(i).start();
        }
    }

    public void stopQueues() {
        for (int i = 0; i < noQueues; i++) {
            queues.get(i).setOpened(false);
        }
    }

    public void setSizes(int[] sizes) {
        for (int i = 0; i < queues.size(); i++) {
            sizes[i] = queues.get(i).getQueue().size();
        }
    }

    public void consolePrint(int[] sizes) {
        System.out.println();
        System.out.println("TIME = " + runningTime);
        System.out.print("Q = ");
        for (Client c : clients) {
            System.out.print("(" + c.getId() + "-" + c.getArrivalTime() + "-" + c.getServiceTime() + ") ");
        }
        if (clients.size() == 0)
            System.out.print("empty");
        System.out.println(" -- size " + clients.size());

        for (Consumer consumer : queues) {
            BlockingQueue<Client> queue = consumer.getQueue();
            int index = queues.indexOf(consumer) + 1;
            System.out.print("q" + index + " = ");
            for (Client c : queue) {
                System.out.print("(" + c.getId() + "-" + c.getArrivalTime() + "-" + c.getServiceTime() + ") ");
            }
            if (queue.size() == 0)
                System.out.print("empty");
            System.out.println(" -- size " + sizes[index - 1]);
        }
    }

    public String filePrint(String s, int[] sizes) {
        s += "TIME = " + runningTime;
        s += "\nQ = ";
        for (Client c : clients) {
            s += "(" + c.getId() + "-" + c.getArrivalTime() + "-" + c.getServiceTime() + ") ";
        }
        if (clients.size() == 0)
            s += "empty";
        s += " -- size " + clients.size() + "\n";

        for (Consumer consumer : queues) {
            BlockingQueue<Client> queue = consumer.getQueue();
            int index = queues.indexOf(consumer) + 1;
            s += "q" + index + " = ";
            for (Client c : queue) {
                s += "(" + c.getId() + "-" + c.getArrivalTime() + "-" + c.getServiceTime() + ") ";
            }
            if (queue.size() == 0)
                s += "empty";
            s += " -- size " + sizes[index - 1] + "\n";
        }
        s += "\n";
        return s;
    }

    public static void write(String s, File f) throws IOException {
        FileWriter fw = new FileWriter(f);
        fw.append(s);
        fw.close();
    }

    public int getPeakHour(int peakHour, int maxPeakHour, int hour) {
        int max = 0;
        for (int i = 0; i < queues.size(); i++) {
            max = max + queues.get(i).getQueue().size();
        }
        if (max > maxPeakHour) {
            peakHour = hour;
        }
        return peakHour;
    }

    public int getMaxPeakHour(int peakHour, int maxPeakHour, int hour) {
        int max = 0;
        for (int i = 0; i < queues.size(); i++) {
            max = max + queues.get(i).getQueue().size();
        }
        if (max > maxPeakHour) {
            maxPeakHour = max;
        }
        return maxPeakHour;
    }

    public float getAverageServingTime(float averageServingTime) {
        for (int i = 0; i < queues.size(); i++) {
            if (queues.get(i).getQueue().isEmpty() == false) averageServingTime++;
        }
        return averageServingTime;
    }

    @Override
    public void run() {
        String stringToWrite = "";
        this.startQueues();
        int[] sizes = new int[queues.size()];
        for (int i = 0; i < queues.size(); i++) {
            sizes[i] = 0;
        }
        ResultView resultView = new ResultView(noClients, noQueues, runningTime, sizes);
        while (runningTime < simulationTime && allClientsWereServed == false) {
            averageWaitingTime = averageServingTime + clients.size();
            while (clients.size() > 0 && clients.get(0).getArrivalTime() == runningTime) {
                int index = getShortestQueue();
                Client client = clients.get(0);
                if (queues.get(index).getQueue().size() == 0) {
                    queues.get(index).setEmpty(true);
                }
                queues.get(index).joinQueue(client);
                queues.get(index).updateWaitingTime();
                clients.remove(client);
            }
            setSizes(sizes);
            consolePrint(sizes);
            stringToWrite = filePrint(stringToWrite, sizes);
            try {
                sleep(1000);
                //sleep(1);
            } catch (InterruptedException e) {
                e.getStackTrace();
            }
            boolean allEmptyQueues = allQueuesAreEmpty();
            if (allEmptyQueues == true && clients.size() == 0) {
                allClientsWereServed = true;
            }
            averageServingTime = getAverageServingTime(averageServingTime);
            peakHour = getPeakHour(peakHour, maxPeakHour, runningTime);
            maxPeakHour = getMaxPeakHour(peakHour, maxPeakHour, runningTime);
            resultView.update(clients.size(), noQueues, runningTime, sizes, averageWaitingTime / noClients, averageServingTime / noClients, peakHour);
            runningTime++;
        }
        setSizes(sizes);
        consolePrint(sizes);
        resultView.update(clients.size(), noQueues, runningTime, sizes, averageWaitingTime / noClients, averageServingTime / noClients, peakHour);
        File f = new File("log.txt");
        try {
            write(stringToWrite, f);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.stopQueues();
    }
}
