package controllers;

import java.util.ArrayList;
import model.Status;
import model.Task;
import model.SubTask;
import model.Epic;


public interface HistoryManager  {

    void add(Task task);

    ArrayList<Task> getHistory();

}
