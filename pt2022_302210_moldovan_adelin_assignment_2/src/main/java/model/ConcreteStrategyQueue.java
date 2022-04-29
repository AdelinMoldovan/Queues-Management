package model;

import java.util.List;

public class ConcreteStrategyQueue  implements Strategy {
    @Override
    public void adaugaClient(List<Server> servere, Client c) {
        int minClienti = servere.get(0).getClienti().size();
        int index = 0;
        int indexMin = 0;
        for (Server s : servere) {
            int nrClienti = s.getClienti().size();
            if (nrClienti < minClienti) {
                minClienti = nrClienti;
                indexMin = index;
            }
            index++;
        }
        servere.get(indexMin).adaugaClient(c);
        servere.get(indexMin).setCounterTime(servere.get(indexMin).getCounterTime()+1);
    }
}