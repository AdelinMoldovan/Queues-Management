package model;

import java.util.List;

public interface Strategy {
    void adaugaClient(List<Server> servers, Client c);
}