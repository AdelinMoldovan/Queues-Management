package model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class FilesUtil {
    public static final String FILE_NAME="simulationOutput3.txt";
    public BufferedWriter writer;

    public FilesUtil() throws IOException{
        writer = new BufferedWriter(new FileWriter(FILE_NAME));
    }

    public void writeData(int timpCurent, List<Client> waitingClienti, List<Server> queue) throws IOException {
        StringBuilder s= new StringBuilder("\nTime: " + timpCurent + "\nWaiting clients: ");
        for(Client c: waitingClienti){
            s.append(c.toString());  //s= s + c.toString();
        }
        for(int i= 0;i<queue.size();i++){
            s.append("\nQueue ").append(i).append(": ");   //s=s+"\nQueue " + i + ": ";
            for(Client c: queue.get(i).getClienti()) {
                s.append(c.toString()); //s= s + c.toString();
            }
        }
        System.out.println(s);
        writer.append(s.toString());
    }

}