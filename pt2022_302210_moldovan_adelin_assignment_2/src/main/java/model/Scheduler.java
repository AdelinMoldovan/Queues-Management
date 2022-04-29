package model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

public class Scheduler {
    private final List<Server> servere;
    private Strategy strategy;

    public Scheduler(int nrMaxServere, int MaxClientiPerServer){
        this.servere= new ArrayList<>();
        for(int i= 0; i<nrMaxServere; i++){
            Server s=new Server(new ArrayBlockingQueue<>(MaxClientiPerServer));
            this.servere.add(s);
        }
    }

    public void changeStrategy(SelectionPolicy policy){
        if(policy == SelectionPolicy.SHORTEST_QUEUE)
        {
            strategy= new ConcreteStrategyQueue();
        }
        if(policy == SelectionPolicy.SHORTEST_TIME)
        {
            strategy= new ConcreteStrategyTime();
        }
    }

    public void dispatchClient(Client c){
        strategy.adaugaClient(servere, c);
    }
    public List<Server> getServere() {
        return servere;
    }
}