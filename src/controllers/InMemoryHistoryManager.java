package controllers;

import java.util.ArrayList;
import model.Task;


public class InMemoryHistoryManager implements  HistoryManager {

    protected final ArrayList<Task> tasksHistory = new ArrayList<>(10);



    @Override
    public void add(Task task) {
        if (task == null) {
            System.out.println("Пустое значение");
            // Или здесь лучше вообще ничего не писать?
        } else {

            if (tasksHistory.size() > 10) {
                tasksHistory.remove(0);
            }

            tasksHistory.add(task);
        }
    }

    @Override
    public ArrayList<Task> getHistory() {
        ArrayList<Task> tasksHistoryToSend;
        tasksHistoryToSend = tasksHistory;
        return tasksHistoryToSend;
    }


}
