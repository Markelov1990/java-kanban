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
        if (tasksHistory.get(10) == null) {
            tasksHistory.add(task);
        } else {
            tasksHistory.remove(0);
            tasksHistory.add(task);
        }
    }

    @Override
    public ArrayList<Task> getHistory() {
        return tasksHistory;
    }
}
