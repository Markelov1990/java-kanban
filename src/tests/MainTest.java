package tests;

import controllers.HistoryManager;
import controllers.InMemoryTaskManager;
import controllers.Managers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
        task1 = new Task("Test addNewTask", "Test addNewTask description", Status.NEW);
        taskId = inMemoryTaskManager.addNewTask(task1);
        task2 = new Task("Test addNewTask2", "Test addNewTask description2", Status.NEW);
        taskId2 = inMemoryTaskManager.addNewTask(task2);
        epic1 = new Epic("Test addNewTask", "Test addNewTask description");
        epicId1 = inMemoryTaskManager.addNewEpic(epic1);
        epic2 = new Epic("Test addNewTask2", "Test addNewTask description2");
        epicId2 = inMemoryTaskManager.addNewEpic(epic2);
        subTask1 = new SubTask("Test addNewTask", "Test addNewTask description", Status.NEW, epic1.getId());
        subTaskId1 = inMemoryTaskManager.addNewSubTask(subTask1);
        subTask2 = new SubTask("Test addNewTask2", "Test addNewTask description2", Status.NEW, epic1.getId());
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