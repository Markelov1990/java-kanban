package controllers;


import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import model.Status;
import model.Task;
import model.SubTask;
import model.Epic;


public class InMemoryTaskManager implements TaskManager {

    protected HashMap<Integer, Task> tasks = new HashMap<>();
    protected HashMap<Integer, Epic> epics = new HashMap<>();
    protected HashMap<Integer, SubTask> subtasks = new HashMap<>();
    protected HistoryManager historyManager = Managers.getDefaultHistory();

    private int identificator = 0;

    public InMemoryTaskManager(HistoryManager historyManager) {

        this.historyManager = historyManager;
    }

    public InMemoryTaskManager() {
    }

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
        updateEpic(epics.get(subtask.getIdOfEpic()));
        return identificator;

    }

    @Override
    public void deleteTasks() {
        tasks.clear();
    }
    @Override
    public void deleteSubtasks() {
        for (Epic epic : epics.values()) {
            epic.cleanSubtaskIds();
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

        historyManager.add(epics.get(id));
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
        Status status2 = Status.INPROGRESS;
        Status status3 = Status.NEW;
        int count = epic.getListOfSubtasks().size();

        SubTask a = (SubTask) epic.getListOfSubtasks().get(0);

        Instant startTime = a.getStartTime();
        Instant endTime = a.getEndTime();


        int countOfDone = 0;
        int countOfInprogress = 0;
        for (int i = 0; i < count; i++) {
            SubTask t = (SubTask) epic.getListOfSubtasks().get(i);
            if (t.getStatus().equals(status1)) {
                countOfDone++;
            } else if (t.getStatus().equals(status2)) {
                countOfInprogress++;
            }
            if (t.getStartTime().isBefore(startTime)) startTime = t.getStartTime();
            if (t.getEndTime().isAfter(endTime)) endTime = t.getEndTime();

        }

        if (count == countOfDone) {
            epic.setStatus("DONE");
        } else if (countOfInprogress > 0 || countOfDone > 0) {
            epic.setStatus("IN_PROGRESS");
        } else {
            epic.setStatus("NEW");
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

        epics.remove(id);

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
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

}
