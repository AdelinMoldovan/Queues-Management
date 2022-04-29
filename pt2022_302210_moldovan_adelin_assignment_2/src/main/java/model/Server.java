package model;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Server implements Runnable {
    private final BlockingQueue<Client> clienti;
    private final AtomicInteger queueTime;

    private int timeInQueue=0;
    private int timeOfServiceInQueue=0;
    private int counterTime =0;
    public int timpulAsteptareCoada =0;
    public int timpulServireCoada =0;
    public int nrClientiPeCoada=0;

    public int getCounterTime() {
        return counterTime;
    }
    public void setCounterTime(int counterTime) {
        this.counterTime = counterTime;
    }
    public int getTimeInQueue() {
        return timeInQueue;
    }
    public void setTimeInQueue(int timeInQueue) {
        this.timeInQueue = timeInQueue;
    }
    public int getTimeOfServiceInQueue() {
        return timeOfServiceInQueue;
    }

    public void setTimeOfServiceInQueue(int timeOfServiceInQueue) {
        this.timeOfServiceInQueue = timeOfServiceInQueue;
    }


    public BlockingQueue<Client> getClienti() {
        return clienti;
    }
    public Server(BlockingQueue<Client> clienti) {
        this.queueTime = new AtomicInteger();
        this.clienti = clienti;
    }

    public void adaugaClient(Client newClient) {
        this.clienti.add(newClient);
        this.queueTime.addAndGet(newClient.getServiceTime());
        newClient.setServiceTime(newClient.getServiceTime() + queueTime.get());
        timpulAsteptareCoada += queueTime.get();
        timpulServireCoada += newClient.getServiceTime();
        nrClientiPeCoada++;
    }

    @Override
    public void run() {
        for (Client t : this.clienti) {
            try {
                wait(t.getServiceTime());

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            setTimeOfServiceInQueue(getTimeOfServiceInQueue()+t.getServiceTime());
            setTimeInQueue(getTimeInQueue()+ t.getServiceTime());
            queueTime.set(queueTime.get() - t.getServiceTime());
            t.setServiceTime(t.getServiceTime() - 1);
        }
    }

//    public Client[] getArrayClienti() {
//        return (Client[]) clienti.toArray();
//    }

}