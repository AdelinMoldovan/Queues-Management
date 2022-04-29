package controller;

import model.SimulationManager;
import view.SimulationView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SimulationController {
    private final SimulationManager simManager;
    private final SimulationView simView;
    private int startSimulation;

    public int getStartSimulation() {
        return startSimulation;
    }
    public void setStartSimulation(int startSimulation) {
        this.startSimulation = startSimulation;
    }

    public SimulationController(SimulationManager simManager, SimulationView simView) {
        this.simManager=simManager;
        this.simView=simView;
        this.setStartSimulation(0);
        this.simView.addSimulationListener(new SimulateListener());
    }

    class SimulateListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            simManager.setTimeLimit(simView.getTimeLimit());
            simManager.setNrClineti(simView.getNrClients());
            simManager.setNrServere(simView.getNrOfQueues());
            simManager.setMinArrivalTime(simView.getMinATime());
            simManager.setMaxArrivalTime(simView.getMaxATime());
            simManager.setMinServiceTime(simView.getMinPTime());
            simManager.setMaxServiceTime(simView.getMaxPTime());
            simManager.scheduler.changeStrategy(simManager.selectionPolicy);
            setStartSimulation(1);
        }
    }
}