package model;

public class Client implements  Comparable<Client>{
    private final int id;
    private final int arrivalTime;
    private int serviceTime;

    public Client(int id, int arrivalTime, int serviceTime, int finishTime) {
        this.id=id;
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
    }

//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }

    public int getArrivalTime() {
        return arrivalTime;
    }

//    public void setArrivalTime(int arrivalTime) {
//        this.arrivalTime = arrivalTime;
//    }

    public int getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(int serviceTime) {
        this.serviceTime = serviceTime;
    }

//    public int getFinishTime() {
//        return finishTime;
//    }
//
//    public void setFinishTime(int finishTime) {
//        this.finishTime = finishTime;
//    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", arrivalTime=" + arrivalTime +
                ", serviceTime=" + serviceTime +
                '}';
    }

    public int  compareTo(Client c){
        if(this.arrivalTime > c.arrivalTime) {
            return 1;
        }
        else
        if(this.arrivalTime == c.arrivalTime){
            return 0;
        }
        return -1;
    }

}