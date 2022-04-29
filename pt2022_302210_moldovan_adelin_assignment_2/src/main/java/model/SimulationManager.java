package model;

import controller.SimulationController;
import view.SimulationView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class SimulationManager implements Runnable {
    private int timeLimit;
    private int minArrivalTime;
    private int maxArrivalTime;
    private int minServiceTime;
    private int maxServiceTime;
    private int nrServere;
    private int nrClineti;
    public int nrMaximClientiPerServer = 100;
    public String str = "";
    public SelectionPolicy selectionPolicy = SelectionPolicy.SHORTEST_TIME;
    public Scheduler scheduler = new Scheduler(getNrServere(), nrMaximClientiPerServer);
    private SimulationView frame;
    private SimulationController controller;
    public List<Client> generatedClienti;
    public FilesUtil fileUtil = new FilesUtil();

    public int timpCurent = 0;
    public int timpMaxim = 0;

    public int getTimeLimit() {
        return timeLimit;
    }
    public void setTimeLimit(int timeLimit) {
        this.timeLimit = timeLimit;
    }

    public int getMinArrivalTime() {
        return minArrivalTime;
    }

    public void setMinArrivalTime(int minArrivalTime) {
        this.minArrivalTime = minArrivalTime;
    }

    public int getMaxArrivalTime() {
        return maxArrivalTime;
    }

    public void setMaxArrivalTime(int maxArrivalTime) {
        this.maxArrivalTime = maxArrivalTime;
    }

    public int getMinServiceTime() {
        return minServiceTime;
    }

    public void setMinServiceTime(int minServiceTime) {
        this.minServiceTime = minServiceTime;
    }

    public int getMaxServiceTime() {
        return maxServiceTime;
    }

    public void setMaxServiceTime(int maxServiceTime) {
        this.maxServiceTime = maxServiceTime;
    }

    public int getNrServere() {
        return nrServere;
    }

    public void setNrServere(int nrServere) {
        this.nrServere = nrServere;
    }

    public int getNrClineti() {
        return nrClineti;
    }

    public void setNrClineti(int nrClineti) {
        this.nrClineti = nrClineti;
    }

    public void generateRandomClients(int numberOfClients) {
        generatedClienti = new ArrayList<>();
        for (int i = 0; i < getNrClineti(); i++) {
            int randomProcessingTime = ThreadLocalRandom.current().nextInt(getMinServiceTime(), getMaxServiceTime() + 1);
            int randomArrivalTime = ThreadLocalRandom.current().nextInt(getMinArrivalTime(), getMaxArrivalTime() + 1);
            Client newClient = new Client(i, randomArrivalTime, randomProcessingTime, randomArrivalTime + randomProcessingTime);
            this.generatedClienti.add(newClient);
        }
        Collections.sort(generatedClienti);
    }

    public SimulationManager() throws IOException, InterruptedException {
        frame = new SimulationView();
        controller = new SimulationController(this, frame);
        frame.setVisible(true);

        while (controller.getStartSimulation() == 0) {
            Thread.sleep(100);
        }
        this.generateRandomClients(getNrClineti());
        {
            this.scheduler = new Scheduler(frame.getNrOfQueues(), nrMaximClientiPerServer);
            for (Server s : this.scheduler.getServere()) {
                new Thread(s).start();
            }
            this.scheduler.changeStrategy(selectionPolicy);
        }
    }

    public void calcTimpMediuAsteptare() throws IOException {
        int sum = 0;
        for (Server s : this.scheduler.getServere()) {
            sum += s.timpulAsteptareCoada;
        }
        double averageTime = (double) sum / (getNrClineti() - generatedClienti.size());
        String st = "\nTimpul mediu de asteptare este: " + averageTime;
        this.fileUtil.writer.append(st);
    }

    public void calcuTimpMediuServire() throws IOException {
        int sum = 0;
        for (Server s : scheduler.getServere()) {
            sum += s.timpulServireCoada;
        }
        double averageTime = (double) sum / (getNrClineti() - generatedClienti.size());
        String st = "\nTimpul mediu de servire este: " + averageTime;
        this.fileUtil.writer.append(st);
    }

    public void oraVarf() {
        int sum = 0;
        int maxsum = 0;
        for (Server s : scheduler.getServere()) {
            sum += s.nrClientiPeCoada;
        }
        if (maxsum < sum) {
            maxsum = sum;
            timpMaxim = timpCurent;
        }
        String st = "\nOra de varf este: " + timpMaxim;
        try {
            this.fileUtil.writer.append(st);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String updateFrame(int currentTime, List<Client> waitingClients, List<Server> queue) throws IOException {
        this.str = "\nTime: " + currentTime + "\nWaiting clients: ";
        for (Client c : waitingClients) {
            this.str = this.str + c.toString();

        }
        for (int i = 0; i < queue.size(); i++) {
            this.str = this.str + "\nQueue " + i + ":";
            for (Client c: queue.get(i).getClienti()) {
                this.str = this.str + c.toString();
            }
        }
        return str;
    }

    @Override
    public void run() {
        while (timpCurent < getTimeLimit() && controller.getStartSimulation() == 1) {
            try { SimulationView.simulationTextArea.append(updateFrame(timpCurent, generatedClienti, scheduler.getServere())); } catch (IOException e) { e.printStackTrace(); }
            try { fileUtil.writeData(timpCurent, generatedClienti, scheduler.getServere()); } catch (IOException e) { e.printStackTrace(); } catch (Exception e) { e.printStackTrace(); }
            List<Client> toRemove = new ArrayList<>();
            for (Client l : generatedClienti) {
                if (l.getArrivalTime() == timpCurent + 1) {
                    scheduler.dispatchClient(l);
                    toRemove.add(l);
                }
            }
            generatedClienti.removeAll(toRemove);
            try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }
            List<Client> toRemove2 = new ArrayList<>();
            for (Server s : scheduler.getServere()) {
                Client l = s.getClienti().peek();
                if (l != null) {
                    if (l.getServiceTime() <= 0) {
                        toRemove2.add(l);
                        s.getClienti().remove(l);
                        s.nrClientiPeCoada--;
                    } else l.setServiceTime(l.getServiceTime() - 1);
                }
            }
            timpCurent++;
        }
        this.oraVarf();
        try { this.calcTimpMediuAsteptare(); } catch (IOException e) { e.printStackTrace(); }
        try { this.calcuTimpMediuServire(); } catch (IOException e) { e.printStackTrace(); }
        try { fileUtil.writer.close(); } catch (IOException e) { e.printStackTrace(); }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        SimulationManager gen = new SimulationManager();
        Thread t = new Thread(gen);
        t.start();
    }
}