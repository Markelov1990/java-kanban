package controllers;

import model.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import static model.TaskType.*;

public class FileBackedTasksManager extends InMemoryTaskManager {
    private final Path path;

    public FileBackedTasksManager(Path path) {
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


        HashMap<Integer, Task> tasks = new HashMap<>();
        HashMap<Integer, Epic> epics = new HashMap<>();
        HashMap<Integer, SubTask> subtasks = new HashMap<>();

        int startId = 0;

        try {
            String file = Files.readString(path);
            String[] rows = file.split(System.lineSeparator());

            for (int i = 1; i < rows.length - 2; i++) {
                Task task = fromString(rows[i]);
                TaskType type = TaskType.valueOf(rows[i].split(",")[1]);

                if (task.getId() > startId) startId = task.getId();




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

            //History manager add

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
        try {
            // Сделать метод

        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка при сохранении данных");
        }
    }

    static private Task fromString(String value) {
        Task task1 = new Task("0", "0", Status.DONE);
        String[] splitValue = value.split(",");
        int id = Integer.parseInt(splitValue[0]);
        TaskType type = TaskType.valueOf(splitValue[1]);
        String name = splitValue[2];
        Status status = Status.valueOf(splitValue[3]);
        String detail = splitValue[4];
        int epicId = 0;


        if (type == TASK) {
            Task task2 =  new Task(name, detail, status);
            task2.setId(id);
            return task2;
        } else if (type == SUBTASK) {
            SubTask subtask2 = new SubTask(name, detail, status, epicId);
            subtask2.setId(id);
            return subtask2;
        } else if (type == EPIC) {
            Epic epic2 = new Epic(name, detail);
            epic2.setId(id);
            return epic2;
        }
        return task1;
    }
}
