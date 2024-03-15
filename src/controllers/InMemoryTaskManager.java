package controllers;


import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

import exceptions.ManagerValidateException;
import model.Status;
import model.Task;
import model.SubTask;
import model.Epic;


public class InMemoryTaskManager implements TaskManager {

    protected HashMap<Integer, Task> tasks = new HashMap<>();
    protected HashMap<Integer, Epic> epics = new HashMap<>();
    protected HashMap<Integer, SubTask> subtasks = new HashMap<>();
    protected HistoryManager historyManager = Managers.getDefaultHistory();
    protected Set<Task> prioritizedTasks = new TreeSet<>(Comparator.comparing(Task::getStartTime));
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
        addNewPrioritizedTask(task);
        return identificator;


    }
    @Override
    public int addNewEpic(Epic epic) {
        identificator++;
        epic.setId(identificator);
        epics.put(identificator, epic);
        addNewPrioritizedTask(epic);
        return identificator;


    }
    @Override
    public int addNewSubTask(SubTask subtask) {
        identificator++;
        subtask.setId(identificator);
        subtasks.put(identificator, subtask);
        epics.get(subtask.getIdOfEpic()).getListOfSubtasks().add(subtask);
        updateEpic(epics.get(subtask.getIdOfEpic()));
        addNewPrioritizedTask(subtask);
        return identificator;

    }

    @Override
    public void deleteTasks() {
        for (Task task : tasks.values()) {
            prioritizedTasks.remove(task);
        }
        tasks.clear();
    }
    @Override
    public void deleteSubtasks() {
        for (Epic epic : epics.values()) {
            epic.cleanSubtaskIds();
            updateEpic(epic);
        }
        for (SubTask subTask : subtasks.values()) {
            prioritizedTasks.remove(subTask);
        }
        subtasks.clear();

    }


    @Override
    public void deleteEpics() {
        for (Epic epic : epics.values()) {
            prioritizedTasks.remove(epic);
        }
        for (SubTask subTask : subtasks.values()) {
            prioritizedTasks.remove(subTask);
        }
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




        int countOfDone = 0;
        int countOfInprogress = 0;
        for (int i = 0; i < count; i++) {
            SubTask t = (SubTask) epic.getListOfSubtasks().get(i);
            if (t.getStatus().equals(status1)) {
                countOfDone++;
            } else if (t.getStatus().equals(status2)) {
                countOfInprogress++;
            }


        }

        if (count == countOfDone) {
            epic.setStatus("DONE");
        } else if (countOfInprogress > 0 || countOfDone > 0) {
            epic.setStatus("IN_PROGRESS");
        } else {
            epic.setStatus("NEW");
        }

        List<SubTask> subtasks = epic.getListOfSubtasks();
        if (!subtasks.isEmpty()) {
            Instant startTime = subtasks.get(0).getStartTime();
            Instant endTime = subtasks.get(0).getEndTime();

            for (SubTask subtask : subtasks) {
                if (subtask.getStartTime().isBefore(startTime)) {
                    startTime = subtask.getStartTime();
                }
                if (subtask.getEndTime().isAfter(endTime)) {
                    endTime = subtask.getEndTime();
                }
            }
            epic.setStartTime(startTime);
            epic.setEndTime(endTime);

            long duration = 0;
            for (SubTask subtask : epic.getListOfSubtasks()) {
                duration = duration + subtask.getDuration();
            }
            epic.setDuration(duration);
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
        prioritizedTasks.remove(tasks.get(id));
        tasks.remove(id);


    }
    @Override
    public void deleteEpic(int id) {

        epics.remove(id);

        for (SubTask subTask : subtasks.values()) {
            if (subTask.getIdOfEpic() == id) {
                prioritizedTasks.remove(subTask);
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
        prioritizedTasks.remove(subtasks.get(id));
        updateEpic(epic);
    }
    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    private void addNewPrioritizedTask(Task task) {
        taskPriority();
        prioritizedTasks.add(task);

    }

    private void taskPriority() {
        List<Task> tasks = getPrioritizedTasks();

        for (int i = 1; i < tasks.size(); i++) {
            Task task = tasks.get(i);

            boolean taskHasIntersections = task.getStartTime().isBefore(tasks.get(i - 1).getEndTime());

            if (taskHasIntersections) {
                throw new ManagerValidateException("Задачи: " + task.getId() + " и " + tasks.get(i - 1).getId() + " пересекаются!");
            }
        }
    }

    private List<Task> getPrioritizedTasks() {

        return new ArrayList<>(prioritizedTasks);

    }

}
