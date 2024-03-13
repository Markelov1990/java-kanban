package controllers;

import exceptions.ManagerSaveException;
import model.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import static model.TaskType.*;

public class FileBackedTasksManager extends InMemoryTaskManager {
    private final Path path;

    public FileBackedTasksManager(Path path) {
        this.path = path;
    }

    public FileBackedTasksManager(HistoryManager historyManager, Path path) {

        super(historyManager);
        this.path = path;
    }

    public FileBackedTasksManager(
            Path path,
            HashMap<Integer, Task> tasks,
            HashMap<Integer, Epic> epics,
            HashMap<Integer, SubTask> subtasks,
            int startId
    ) {

        this.path = path;
        this.tasks = tasks;
        this.epics = epics;
        this.subtasks = subtasks;

    }

    public static FileBackedTasksManager loadFromFile(Path path) {
        if (!Files.exists(path)) {
            return new FileBackedTasksManager(path);
        }
        HashMap<Integer, Task> allTasks = new HashMap<>();
        HashMap<Integer, Task> tasks = new HashMap<>();
        HashMap<Integer, Epic> epics = new HashMap<>();
        HashMap<Integer, SubTask> subtasks = new HashMap<>();
        HistoryManager historyManager = new InMemoryHistoryManager();
        int startId = 0;

        try {
            String file = Files.readString(path);
            String[] rows = file.split(System.lineSeparator());

            for (int i = 1; i < rows.length - 2; i++) {
                Task task = fromString(rows[i]);
                TaskType type = TaskType.valueOf(rows[i].split(",")[1]);

                if (task.getId() > startId) startId = task.getId();
                allTasks.put(task.getId(), task);


                if (type == TASK) {
                    tasks.put(task.getId(), task);
                } else if (type == SUBTASK) {
                    SubTask subtask = (SubTask) task;
                    subtasks.put(task.getId(), subtask);
                    epics.get(subtask.getIdOfEpic()).getListOfSubtasks().add(subtask);
                } else if (type == EPIC) {
                    epics.put(task.getId(), (Epic) task);
                }

            }

            List<Integer> historyList = historyFromString(rows[rows.length - 1]);

            historyList.forEach(id -> historyManager.add(allTasks.get(id)));



        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка при восстановлении данных");
        }

        return new FileBackedTasksManager(path, tasks, epics, subtasks, startId);
    }

    @Override
    public Task getTask(int id) {
        Task task = super.getTask(id);
        save();
        return task;
    }

    @Override
    public Epic getEpic(int id) {
        Epic epic = super.getEpic(id);
        save();
        return epic;
    }

    @Override
    public SubTask getSubtask(int id) {
        SubTask subtask = super.getSubtask(id);
        save();
        return subtask;
    }

    @Override
    public int addNewTask(Task task) {
        int id = super.addNewTask(task);
        save();
        return id;
    }

    @Override
    public int addNewEpic(Epic epic) {
        int id = super.addNewEpic(epic);
        save();
        return id;
    }

    @Override
    public int addNewSubTask(SubTask subtask) {
        int id = super.addNewSubTask(subtask);
        save();
        return id;
    }


    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
        save();

    }


    @Override
    public void deleteTask(int id) {
        super.deleteTask(id);
        save();
    }

    @Override
    public void deleteEpic(int id) {
        super.deleteEpic(id);
        save();
    }

    @Override
    public void deleteSubtask(int id) {
        super.deleteSubtask(id);
        save();
    }

    @Override
    public void deleteTasks() {
        super.deleteTasks();
        save();
    }

    @Override
    public void deleteEpics() {
        super.deleteEpics();
        save();
    }

    @Override
    public void deleteSubtasks() {
        super.deleteSubtasks();
        save();
    }

    protected void save() {


        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path.toFile()))) {


            bufferedWriter.write("id,type,name,status,detail, startTime, duration, epic");
            for (Task task : tasks.values()) {
                String stringTask = String.format("%s,%s,%s,%s,%s,%s,%s,%s", task.getId(), "TASK", task.getName(), task.getStatus(), task.getDetail(), task.getStartTime(), task.getDuration());
                bufferedWriter.newLine();
                bufferedWriter.write(stringTask);
            }
            for (Epic epic : epics.values()) {
                String stringEpic = String.format("%s,%s,%s,%s,%s,%s,%s,%s", epic.getId(), "EPIC", epic.getName(), epic.getStatus(), epic.getDetail(), epic.getStartTime(), epic.getDuration());
                bufferedWriter.newLine();
                bufferedWriter.write(stringEpic);
            }
            for (SubTask subtask : subtasks.values()) {
                String stringSubtask = String.format("%s,%s,%s,%s,%s,%s,%s,%s", subtask.getId(), "EPIC", subtask.getName(), subtask.getStatus(), subtask.getDetail(), subtask.getIdOfEpic(), subtask.getStartTime(), subtask.getDuration());
                bufferedWriter.newLine();
                bufferedWriter.write(stringSubtask);
            }
            bufferedWriter.write(historyToString(historyManager));


        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка при сохранении данных");
        }

    }

    private static Task fromString(String value) {
        Task task1 = new Task("0", "0", Status.DONE, Instant.now(), 30);
        String[] splitValue = value.split(",");
        int id = Integer.parseInt(splitValue[0]);
        TaskType type = valueOf(splitValue[1]);
        String name = splitValue[2];
        Status status = Status.valueOf(splitValue[3]);
        String detail = splitValue[4];
        Instant startTime = Instant.ofEpochMilli(Long.parseLong(splitValue[5]));
        long duration = Long.parseLong(splitValue[6]);
        int epicId = 0;


        if (type == TASK) {
            Task task2 = new Task(name, detail, status, startTime, duration);
            task2.setId(id);
            return task2;
        } else if (type == SUBTASK) {
            SubTask subtask2 = new SubTask(name, detail, status, epicId, startTime, duration);
            subtask2.setId(id);
            return subtask2;
        } else if (type == EPIC) {
            Epic epic2 = new Epic(name, detail, startTime, duration);
            epic2.setId(id);
            return epic2;
        }
        return task1;
    }


    private static String historyToString(HistoryManager manager) {
        List<Task> history = manager.getHistory();
        StringBuilder str = new StringBuilder();

        if (history.isEmpty()) {
            return "";
        }

        for (Task task : history) {
            str.append(task.getId()).append(",");
        }

        if (str.length() != 0) {
            str.deleteCharAt(str.length() - 1);
        }

        return str.toString();
    }


    private static List<Integer> historyFromString(String value) {
        List<Integer> list = new ArrayList<>();
        if (value != null) {
            String[] value1 = value.split(",");;
            for (String number : value1) {
                list.add(Integer.parseInt(number));
            }
            return list;
        }
        return list;
    }
}
