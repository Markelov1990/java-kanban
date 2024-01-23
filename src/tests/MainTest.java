package tests;

import controllers.HistoryManager;
import controllers.Managers;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import controllers.TaskManager;
import model.Status;
import model.Task;
import model.SubTask;
import model.Epic;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    // Тест из описания ТЗ
    @Test
    void addNewTaskTest() {
        TaskManager inMemoryTaskManager = Managers.getDefault();
        Task task = new Task("Test addNewTask", "Test addNewTask description", Status.NEW);
        final int taskId = inMemoryTaskManager.addNewTask(task);

        final Task savedTask = inMemoryTaskManager.getTask(taskId);

        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(task, savedTask, "Задачи не совпадают.");

        final ArrayList<Task> tasks = inMemoryTaskManager.getTasks();

        assertNotNull(tasks, "Задачи не возвращаются.");
        assertEquals(1, tasks.size(), "Неверное количество задач.");
        assertEquals(task, tasks.get(0), "Задачи не совпадают.");
    }

    // Тест из описания ТЗ
    @Test
    void historyIsNotEmptyTest() {
        HistoryManager historyManager = Managers.getDefaultHistory();
        Task task = new Task("Test addNewTask", "Test addNewTask description", Status.NEW);
        historyManager.add(task);
        final ArrayList<Task> history = historyManager.getHistory();
        assertNotNull(history, "История не пустая.");
        assertEquals(1, history.size(), "История не пустая.");
    }

    // 1 Тест проверьте, что экземпляры класса Task равны друг другу, если равен их id;

    @Test
    void checkEqualsTaskTest() {
        TaskManager inMemoryTaskManager = Managers.getDefault();
        Task task1 = new Task("Test addNewTask", "Test addNewTask description", Status.NEW);
        Task task2 = new Task("Test addNewTask2", "Test addNewTask description2", Status.NEW);
        final int taskId = inMemoryTaskManager.addNewTask(task1);
        final int taskId2 = inMemoryTaskManager.addNewTask(task2);

        assertEquals(task1, inMemoryTaskManager.getTask(taskId), "Задачи не совпадают.");
        assertEquals(task2, inMemoryTaskManager.getTask(taskId2), "Задачи не совпадают.");
        assertNotEquals(task1,task2, "Задачи совпали, а не должны были");


    }
    // 2 Тест проверьте, что экземпляры класса Task равны друг другу, если равен их id;

    @Test
    void checkEqualsEpicTest() {
        TaskManager inMemoryTaskManager = Managers.getDefault();
        Epic epic1 = new Epic("Test addNewTask", "Test addNewTask description");
        Epic epic2 = new Epic("Test addNewTask2", "Test addNewTask description2");
        final int epicId1 = inMemoryTaskManager.addNewEpic(epic1);
        final int epicId2 = inMemoryTaskManager.addNewEpic(epic2);

        assertEquals(epic1, inMemoryTaskManager.getEpic(epicId1), "Задачи не совпадают.");
        assertEquals(epic2, inMemoryTaskManager.getEpic(epicId2), "Задачи не совпадают.");
        assertNotEquals(inMemoryTaskManager.getEpic(epicId1),inMemoryTaskManager.getEpic(epicId2), "Задачи совпали, а не должны были");

    }

    // 2 Тест проверьте, что экземпляры класса Task равны друг другу, если равен их id;
    @Test
    void checkEqualsSubtaskTest() {
        TaskManager inMemoryTaskManager = Managers.getDefault();
        Epic epic1 = new Epic("Test addNewTask", "Test addNewTask description");
        final int epicId1 = inMemoryTaskManager.addNewEpic(epic1);
        SubTask subTask1 = new SubTask("Test addNewTask", "Test addNewTask description", Status.NEW, epicId1);
        SubTask subTask2 = new SubTask("Test addNewTask2", "Test addNewTask description2", Status.NEW, epicId1);
        final int subTaskId1 = inMemoryTaskManager.addNewSubTask(subTask1);
        final int subTaskId2 = inMemoryTaskManager.addNewSubTask(subTask2);

        assertEquals(subTask1, inMemoryTaskManager.getSubtask(subTaskId1), "Задачи не совпадают.");
        assertEquals(subTask2, inMemoryTaskManager.getSubtask(subTaskId2), "Задачи не совпадают.");
        assertNotEquals(subTask1,subTask2, "Задачи совпали, а не должны были");

    }

    /*проверьте, что объект Epic нельзя добавить в самого себя в виде подзадачи;
    Это просто невозможно, они по разному вносятся и в разные Мапы попадают
    Когда я с интерактивом делал, там ещё была возможность того, что неверно будет задан параметр ввода, но не здесь

    проверьте, что объект Subtask нельзя сделать своим же эпиком;
     Тут аналогично. Разный ввод для эпиков и подзадач.
     Подскажите есть ли впринципе такой вариант, но пока даже не представляю как это проверить.
     Разве, что ввести их с одинаковым названием и одинаковым описанием и проверить. Но думаю задача не в этом.

     */

    //убедитесь, что утилитарный класс всегда возвращает проинициализированные и готовые к работе экземпляры менеджеров;
    @Test
    void managersReturnClassCheck() {
        HistoryManager historyManager = Managers.getDefaultHistory();
        Task task = new Task("Test addNewTask", "Test addNewTask description", Status.NEW);
        historyManager.add(task);
        final ArrayList<Task> history = historyManager.getHistory();
        assertNotNull(history, "История пустая.");
        TaskManager inMemoryTaskManager = Managers.getDefault();
        Task task1 = new Task("Test addNewTask", "Test addNewTask description", Status.NEW);
        final int taskId = inMemoryTaskManager.addNewTask(task1);
        assertNotNull(inMemoryTaskManager.getTask(taskId), "Задача пустая");

    }
    //проверьте, что InMemoryTaskManager действительно добавляет задачи разного типа и может найти их по id;
    @Test
    void checkAddedTasksAndFindById() {
        TaskManager inMemoryTaskManager = Managers.getDefault();
        Task task1 = new Task("Test addNewTask", "Test addNewTask description", Status.NEW);
        final int taskId = inMemoryTaskManager.addNewTask(task1);
        Epic epic1 = new Epic("Test addNewTask", "Test addNewTask description");
        final int epicId1 = inMemoryTaskManager.addNewEpic(epic1);
        SubTask subTask1 = new SubTask("Test addNewTask", "Test addNewTask description", Status.NEW, epicId1);
        final int subTaskId1 = inMemoryTaskManager.addNewSubTask(subTask1);


        assertEquals(task1,inMemoryTaskManager.getTask(taskId), "Задачи не совпадают.");
        assertEquals(epic1,inMemoryTaskManager.getEpic(epicId1), "Задачи не совпадают.");
        assertEquals(subTask1,inMemoryTaskManager.getSubtask(subTaskId1), "Задачи не совпадают.");
        //Этот функционал и выше реализован, тут я так полнял больше проверка на возможность добавления разных задач

    }
    //проверьте, что задачи с заданным id и сгенерированным id не конфликтуют внутри менеджера;
    // Так в программе нет ни одного варианта где ИД заданное, оно везде сгенерированое.


    //убедитесь, что задачи, добавляемые в HistoryManager, сохраняют предыдущую версию задачи и её данных.
    @Test
    void historyCheckOldAndNewVersionEmptyTest() {
        TaskManager inMemoryTaskManager = Managers.getDefault();
        Task task = new Task("Test addNewTask", "Test addNewTask description", Status.NEW);
        final int taskId = inMemoryTaskManager.addNewTask(task);
        inMemoryTaskManager.getTask(taskId);
        inMemoryTaskManager.getTask(taskId).setStatus("DONE");
        inMemoryTaskManager.getTask(taskId);

        assertEquals(inMemoryTaskManager.getHistory().get(0), inMemoryTaskManager.getHistory().get(1), "Задачи совпали, а не должны были");


    }
}