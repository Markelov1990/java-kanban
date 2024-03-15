package controllers;


import java.util.ArrayList;
import java.util.List;
import model.Task;
import model.SubTask;
import model.Epic;


public interface TaskManager {

    int addNewTask(Task task);

    int addNewEpic(Epic epic);

    int addNewSubTask(SubTask subtask);

    void deleteTasks();

    void deleteSubtasks();

    void deleteEpics();

    Task getTask(int id);

    SubTask getSubtask(int id);

    Epic getEpic(int id);

    SubTask getEpicSubtasks(int idOfEpic);

    void updateEpic(Epic epic);

    ArrayList<Task> getTasks();

    ArrayList<Epic> getEpics();

    ArrayList<SubTask> getSubtasks();

    void deleteTask(int id);

    void deleteEpic(int id);

    void deleteSubtask(int id);

    List<Task> getHistory();
}
