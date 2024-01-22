package controllers;

import java.util.ArrayList;
import model.Status;
import model.Task;
import model.SubTask;
import model.Epic;

public class InMemoryHistoryManager implements  HistoryManager {

    protected final ArrayList<Task> tasksHistory = new ArrayList<>(10);



    @Override
    public void add(Task task) {
        if (tasksHistory.size()>10) {
            tasksHistory.remove(0);
            tasksHistory.add(task);
        } else {
            tasksHistory.add(task);
        }


    }

    @Override
    public ArrayList<Task> getHistory() {
        return tasksHistory;
    }

    public void checkIsEmpty () {

    }
}
