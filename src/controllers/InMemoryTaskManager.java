package controllers;


import java.util.ArrayList;
import java.util.HashMap;

import model.Status;
import model.Task;
import model.SubTask;
import model.Epic;


public class InMemoryTaskManager implements TaskManager {

    protected final HashMap<Integer, Task> tasks = new HashMap<>();
    protected final HashMap<Integer, Epic> epics = new HashMap<>();
    protected final HashMap<Integer, SubTask> subtasks = new HashMap<>();
    protected final ArrayList<Task> tasksHistory = new ArrayList<>(10);
    protected final HistoryManager historyManager = Managers.getDefaultHistory();

    private int identificator = 0;

    @Override
    public int addNewTask(Task task) {
        identificator++;
        task.setId(identificator);
        tasks.put(identificator, task);
        return identificator;


    }
    @Override
    public int addNewEpic(Epic epic) {
        identificator++;
        epic.setId(identificator);
        epics.put(identificator, epic);
        return identificator;


    }
    @Override
    public int addNewSubTask(SubTask subtask) {
        identificator++;
        subtask.setId(identificator);
        subtasks.put(identificator, subtask);
        epics.get(subtask.getIdOfEpic()).getListOfSubtasks().add(subtask);
        return identificator;

    }

    @Override
    public void deleteTasks() {
        tasks.clear();
    }
    @Override
    public void deleteSubtasks() {
        for (Epic epic : epics.values()) {
            epic.cleanSubtaskIds(); //Как и писал до этого определнных ИД нет и их списка тоже, потому сделал метод по удалению всех подзадач в эпике.
            updateEpic(epic);
        }
        subtasks.clear();
    }


    @Override
    public void deleteEpics() {
        epics.clear();
        subtasks.clear();
    }

    @Override
    public Task getTask(int id) {
        historyManager.add(tasks.get(id));
        return tasks.get(id);
    }
    @Override
    public SubTask getSubtask(int id) {

        historyManager.add(subtasks.get(id));
        return subtasks.get(id);
    }
    @Override
    public Epic getEpic(int id) {

        historyManager.add(subtasks.get(id));
        return epics.get(id);
    }
    @Override
    public SubTask getEpicSubtasks(int idOfEpic) {
        SubTask transitSubtask = null;
        for (int a : subtasks.keySet()) {
            if (idOfEpic == subtasks.get(a).getIdOfEpic()) {
                transitSubtask = subtasks.get(a);
                return transitSubtask;
            }

        }
        return null;
    }
    @Override
    public void updateEpic(Epic epic) {
        Status status1 = Status.DONE;
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


        if (count < countOfDone) {
            epic.setStatus("DONE");
        }
    }


    @Override
    public ArrayList<Task> getTasks() {
        return new ArrayList<>(tasks.values());
    }
    @Override
    public ArrayList<Epic> getEpics() {
        return new ArrayList<>(epics.values());
    }
    @Override
    public ArrayList<SubTask> getSubtasks() {
        return new ArrayList<>(subtasks.values());
    }
    @Override
    public void deleteTask(int id) {

        tasks.remove(id);

    }
    @Override
    public void deleteEpic(int id) {

        final Epic epic = epics.remove(id);

        for (SubTask subTask : subtasks.values()) {
            if (subTask.getIdOfEpic() == id) {
                subtasks.remove(subTask.getId());
            }
        }

    }
    @Override
    public void deleteSubtask(int id) {
        SubTask subtask = subtasks.remove(id);
        if (subtask == null) {
            return;
        }
        Epic epic = epics.get(subtask.getIdOfEpic());
        epic.getListOfSubtasks().remove(id);
        updateEpic(epic);
    }
    @Override
    public ArrayList<Task> getHistory() {
        return tasksHistory;
    }
}
