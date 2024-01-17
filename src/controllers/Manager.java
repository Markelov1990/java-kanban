package controllers;


import java.util.HashMap;

import model.Status;
import model.Task;
import model.SubTask;
import model.Epic;


public class Manager {

    protected final HashMap<Integer, Task> tasks = new HashMap<>();
    protected final HashMap<Integer, Epic> epics = new HashMap<>();
    protected final HashMap<Integer, SubTask> subtasks = new HashMap<>();

    int identificator = 0;
    private String name;
    private String detail;
    Status status;
    int id;




    public int addNewTask(Task task) {
        identificator++;
        task.setId(identificator);
                tasks.put(identificator,task);
                return identificator;


    }

    public int addNewEpic(Epic epic) {
        identificator++;
        epic.setId(identificator);
        epics.put(identificator,epic);
        return identificator;


    }

    public int addNewSubTask(SubTask subtask) {
        identificator++;
        subtask.setId(identificator);
        subtasks.put(identificator,subtask);
        epics.get(subtask.getIdOfEpic()).getListOfSubtasks().add(subtask);
        return identificator;

    }





    public void removeAllTasks() {

        tasks.clear();
        subtasks.clear();
        epics.clear();

    }



    public Task getTask(int id) {
        Task transitTask = null;
        for (int a: tasks.keySet()) {
            if (a == id) {
                transitTask = tasks.get(id);
            }
        }
        return transitTask;
    }

    public SubTask getSubtask(int id) {
        SubTask transitSubtask = null;
        for (int a: subtasks.keySet()) {
            if (a == id) {
                transitSubtask = subtasks.get(id);
            }
        }
        return transitSubtask;
    }

    public Epic getEpic(int id) {
        Epic transitEpic = null;
        for (int a: epics.keySet()) {
            if (a == id) {
                transitEpic = epics.get(id);
            }
        }
        return transitEpic;
    }

    public SubTask getEpicSubtasks (int idOfEpic) {
        SubTask transitSubtask = null;
        for (int a: subtasks.keySet()) {
            if (idOfEpic == subtasks.get(a).getIdOfEpic()) {
                transitSubtask = subtasks.get(a);
                return transitSubtask;
            }

        }
        return null;
    }

    public void updateEpic (Epic epic) {
        Status status1 = Status.valueOf("DONE");
            int b = epic.getId() + 1;
            int count = 0;
            for (SubTask t : subtasks.values()) {
                if (t.getIdOfEpic() == epic.getId()) {
                    count++;
                }
            }

        int countOfDone = 0;
        for (SubTask t : subtasks.values()) {
            if (t.getStatus().equals(status1)) {
                countOfDone++;
            }
        }

        if (count == countOfDone) {
            epic.setStatus("DONE");
        }
        }


    public HashMap<Integer, Task> getTasks() {

        return tasks;
    }

    public HashMap<Integer, Epic> getEpics() {

        return epics;
    }

    public HashMap<Integer, SubTask> getSubtasks() {

        return subtasks;
    }

    public void deleteTask(int id) {
        tasks.remove(id);
    }

    public void deleteEpic(int id) {
        epics.remove(id);
    }


}
