import controllers.InMemoryTaskManager;
import controllers.Managers;
import controllers.TaskManager;
import model.Status;
import model.Task;
import model.SubTask;
import model.Epic;
import java.time.Instant;


public class Main {

    public static void main(String[] args) {

        TaskManager inMemoryTaskManager = Managers.getDefault();
        Task task1 = new Task("model.Task #1", "Task1 description", Status.NEW, Instant.now(), 30);
        Task task2 = new Task("model.Task #2", "Task2 description", Status.INPROGRESS, Instant.now(), 30);
        final int taskId1 = inMemoryTaskManager.addNewTask(task1);
        final int taskId2 = inMemoryTaskManager.addNewTask(task2);
        Epic epic1 = new Epic("model.Epic #1", "Epic1 description", Instant.now(),30);
        Epic epic2 = new Epic("model.Epic #2", "Epic2 description", Instant.now(), 30);
        final int epicId1 = inMemoryTaskManager.addNewEpic(epic1);
        final int epicId2 = inMemoryTaskManager.addNewEpic(epic2);
        SubTask subtask1 = new SubTask("Subtask #1-1", "Subtask1 description", Status.NEW, epicId1, Instant.now(), 15);
        SubTask subtask2 = new SubTask("Subtask #2-1", "Subtask1 description", Status.NEW, epicId1, Instant.now(), 15);
        SubTask subtask3 = new SubTask("Subtask #3-2", "Subtask1 description", Status.NEW, epicId2, Instant.now(), 15);
        final Integer subtaskId1 = inMemoryTaskManager.addNewSubTask(subtask1);
        final Integer subtaskId2 = inMemoryTaskManager.addNewSubTask(subtask2);
        final Integer subtaskId3 = inMemoryTaskManager.addNewSubTask(subtask3);
        printAllTasks(inMemoryTaskManager);
        // Обновление

        final Task task = inMemoryTaskManager.getTask(taskId2);
        task.setStatus("DONE");
        //manager.updateTask(task); - не совсем понимаю, зачем нужен этот метод, если статус меняется в предедущей строчке.
        System.out.println("CHANGE STATUS: Task2 IN_PROGRESS->DONE");
        System.out.println("Задачи:");
        for (Task t : inMemoryTaskManager.getTasks()) {
            System.out.println(t);
        }
        SubTask subtask = inMemoryTaskManager.getSubtask(subtaskId2);
        subtask.setStatus("DONE");
        inMemoryTaskManager.updateEpic(epic1);
        System.out.println("CHANGE STATUS: Subtask2 NEW->DONE");
        subtask = inMemoryTaskManager.getSubtask(subtaskId3);
        subtask.setStatus("DONE");
        inMemoryTaskManager.updateEpic(epic2);
        System.out.println("CHANGE STATUS: Subtask3 NEW->DONE");
        System.out.println("Подзадачи:");
        for (Task t : inMemoryTaskManager.getSubtasks()) {
            System.out.println(t);
        }
        System.out.println("Эпики:");
        for (Epic e : inMemoryTaskManager.getEpics()) {
            System.out.println(e);

        }
        final Epic epic = inMemoryTaskManager.getEpic(epicId1);
        epic.setStatus("NEW");
        inMemoryTaskManager.updateEpic(epic1);
        System.out.println("CHANGE STATUS: Epic1 IN_PROGRESS->NEW");
        printAllTasks(inMemoryTaskManager);
        System.out.println("Эпики:");
        for (Task e : inMemoryTaskManager.getEpics()) {
            System.out.println(e);

        }


        // Удаление
        System.out.println("DELETE: Task1");
        inMemoryTaskManager.deleteTask(taskId1);
        System.out.println("DELETE: Epic2");
        inMemoryTaskManager.deleteEpic(epicId2);
        printAllTasks(inMemoryTaskManager);

        inMemoryTaskManager.deleteTasks();
        inMemoryTaskManager.deleteSubtasks();
        inMemoryTaskManager.deleteEpics();
    }




    private static void printAllTasks(TaskManager inMemoryTaskManager) {
        System.out.println("Задачи:");
        for (Task task : inMemoryTaskManager.getTasks()) {
            System.out.println(task);
        }

        System.out.println("Эпики:");
        for (Task epic : inMemoryTaskManager.getEpics()) {
            System.out.println(epic);


        }
            System.out.println("Подзадачи:");
            for (Task subtask : inMemoryTaskManager.getSubtasks()) {
                System.out.println(subtask);
            }

        System.out.println("История:");
        for (Task task : inMemoryTaskManager.getHistory()) {
            System.out.println(task);
        }
        }
    }

