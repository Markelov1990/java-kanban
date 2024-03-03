package controllers;

import java.util.ArrayList;
import java.util.List;

import model.Task;



public interface HistoryManager  {

    void add(Task task);

    List<Task> getHistory();

    void remove(int id);


}
