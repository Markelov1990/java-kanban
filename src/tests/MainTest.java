package tests;

import controllers.HistoryManager;
import controllers.InMemoryTaskManager;
import controllers.Managers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import controllers.TaskManager;
import model.Status;
import model.Task;
import model.SubTask;
import model.Epic;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {
    protected TaskManager inMemoryTaskManager = Managers.getDefault();
    protected HistoryManager historyManager = Managers.getDefaultHistory();
    protected Task task1;
    protected Task task2;
    protected Epic epic1;
    protected Epic epic2;
    protected SubTask subTask1;
    protected SubTask subTask2;
    protected int taskId;
    protected int taskId2;
    protected int epicId1;
    protected int epicId2;
    protected int subTaskId1;
    protected int subTaskId2;

    @BeforeEach
    void beforeEach(){

        task1 = new Task("model.Task #1", "Task1 description", Status.NEW, Instant.ofEpochMilli(1689445500000L), 1);
        taskId = inMemoryTaskManager.addNewTask(task1);
        task2 = new Task("model.Task #2", "Task2 description", Status.NEW, Instant.ofEpochMilli(2682336600000L), 2);
        taskId2 = inMemoryTaskManager.addNewTask(task2);
        epic1 = new Epic("model.Epic #1", "Epic1 description", Instant.ofEpochMilli(3681003300000L),3);
        epicId1 = inMemoryTaskManager.addNewEpic(epic1);
        epic2 = new Epic("model.Epic #2", "Epic2 description", Instant.ofEpochMilli(4671003300000L), 4);
        epicId2 = inMemoryTaskManager.addNewEpic(epic2);
        subTask1 = new SubTask("Subtask #1-1", "Subtask1 description", Status.NEW, epicId1, Instant.ofEpochMilli(5691003300000L), 5);
        subTaskId1 = inMemoryTaskManager.addNewSubTask(subTask1);
        subTask2 = new SubTask("Subtask #2-1", "Subtask1 description", Status.NEW, epicId1, Instant.ofEpochMilli(669903300000L), 6);
        subTaskId2 = inMemoryTaskManager.addNewSubTask(subTask2);

    }



    @Test
    void addNewTaskTest() {

        final Task savedTask = inMemoryTaskManager.getTask(taskId);

        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(task1, savedTask, "Задачи не совпадают.");

        final ArrayList<Task> tasks = inMemoryTaskManager.getTasks();

        assertNotNull(tasks, "Задачи не возвращаются.");
        assertEquals(1, tasks.size(), "Неверное количество задач.");
        assertEquals(task1, tasks.get(0), "Задачи не совпадают.");
    }

    @Test
    void historyIsNotEmptyTest() {

        historyManager.add(task1);
        final List<Task> history = historyManager.getHistory();
        assertNotNull(history, "История не пустая.");
        assertEquals(1, history.size(), "История не пустая.");
    }


    @Test
    void checkEqualsTaskTest() {

        assertEquals(task1, inMemoryTaskManager.getTask(taskId), "Задачи не совпадают.");
        assertEquals(task2, inMemoryTaskManager.getTask(taskId2), "Задачи не совпадают.");
        assertNotEquals(task1,task2, "Задачи совпали, а не должны были");


    }
    @Test
    void checkEqualsSubtaskTest() {

        assertEquals(subTask1, inMemoryTaskManager.getSubtask(subTaskId1), "Задачи не совпадают.");
        assertEquals(subTask2, inMemoryTaskManager.getSubtask(subTaskId2), "Задачи не совпадают.");
        assertNotEquals(subTask1,subTask2, "Задачи совпали, а не должны были");

    }




    @Test
    void checkEqualsEpicTest() {

        final int epicId1 = inMemoryTaskManager.addNewEpic(epic1);
        final int epicId2 = inMemoryTaskManager.addNewEpic(epic2);

        assertEquals(epic1, inMemoryTaskManager.getEpic(epicId1), "Задачи не совпадают.");
        assertEquals(epic2, inMemoryTaskManager.getEpic(epicId2), "Задачи не совпадают.");
        assertNotEquals(inMemoryTaskManager.getEpic(epicId1),inMemoryTaskManager.getEpic(epicId2), "Задачи совпали, а не должны были");

    }




    @Test
    void managersReturnClassCheck() {

        historyManager.add(task1);
        final List<Task> history = historyManager.getHistory();
        assertNotNull(history, "История пустая.");
        final int taskId = inMemoryTaskManager.addNewTask(task1);
        assertNotNull(inMemoryTaskManager.getTask(taskId), "Задача пустая");

    }

    @Test
    void checkAddedTasksAndFindById() {

        assertEquals(task1,inMemoryTaskManager.getTask(taskId), "Задачи не совпадают.");
        assertEquals(epic1,inMemoryTaskManager.getEpic(epicId1), "Задачи не совпадают.");
        assertEquals(subTask1,inMemoryTaskManager.getSubtask(subTaskId1), "Задачи не совпадают.");
    }

    @Test
    void historyCheckOldAndNewVersionEmptyTest() {

        inMemoryTaskManager.getTask(taskId);
        inMemoryTaskManager.getTask(taskId).setStatus("DONE");
        inMemoryTaskManager.getTask(taskId);


        assertEquals(inMemoryTaskManager.getHistory().size(), 1, "Одна и та же задача запрошена дважды, но в истории сохранилось две");
    }

}