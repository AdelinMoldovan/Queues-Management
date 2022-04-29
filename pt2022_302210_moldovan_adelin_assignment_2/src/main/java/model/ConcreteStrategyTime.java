package model;

import java.util.List;

public class ConcreteStrategyTime implements Strategy {
    @Override
    public void adaugaClient(List<Server> servere, Client c){
        int min=Integer.MAX_VALUE;
        int index=0;
        int indexMin=0;
        for(Server s: servere){
            int sum=0;
            for (Client ts: s.getClienti()){
                sum+=ts.getServiceTime();
            }
            if(min>sum){
                min=sum;
                indexMin=index;
            }
            index++;
        }
        servere.get(indexMin).adaugaClient(c);
        servere.get(indexMin).setCounterTime( servere.get(indexMin).getCounterTime()+1);
    }

}
