import model.Status;
import model.Task;
import model.SubTask;
import model.Epic;
import controllers.Manager;




public class Main {

    public static void main(String[] args) {

        Manager manager = new Manager();
        Task task1 = new Task("model.Task #1", "Task1 description", "NEW");
        Task task2 = new Task("model.Task #2", "Task2 description", "IN_PROGRESS");
        final int taskId1 = manager.addNewTask(task1);
        final int taskId2 = manager.addNewTask(task2);
        Epic epic1 = new Epic("model.Epic #1", "Epic1 description", "NEW");
        Epic epic2 = new Epic("model.Epic #2", "Epic2 description", "NEW");
        final int epicId1 = manager.addNewEpic(epic1);
        final int epicId2 = manager.addNewEpic(epic2);
        SubTask subtask1 = new SubTask("Subtask #1-1", "Subtask1 description", "NEW", epicId1);
        SubTask subtask2 = new SubTask("Subtask #2-1", "Subtask1 description", "NEW", epicId1);
        SubTask subtask3 = new SubTask("Subtask #3-2", "Subtask1 description", "NEW", epicId2);
        final Integer subtaskId1 = manager.addNewSubTask(subtask1);
        final Integer subtaskId2 = manager.addNewSubTask(subtask2);
        final Integer subtaskId3 = manager.addNewSubTask(subtask3);
        printAllTasks(manager);
        // Обновление

        final Task task = manager.getTask(taskId2);
        task.setStatus("DONE");
        //manager.updateTask(task); - не совсем понимаю, зачем нужен этот метод, если статус меняется в предедущей строчке.
        System.out.println("CHANGE STATUS: Task2 IN_PROGRESS->DONE");
        System.out.println("Задачи:");
        for (Task t : manager.getTasks().values()) {
            System.out.println(t);
        }
        SubTask subtask = manager.getSubtask(subtaskId2);
        subtask.setStatus("DONE");
        //manager.updateSubtask(subtask); - не совсем понимаю, зачем нужен этот метод, если статус меняется в предедущей строчке.
        System.out.println("CHANGE STATUS: Subtask2 NEW->DONE");
        subtask = manager.getSubtask(subtaskId3);
        subtask.setStatus("DONE");
        manager.updateEpic(epic2);
        //manager.updateSubtask(subtask); - не совсем понимаю, зачем нужен этот метод, если статус меняется в предедущей строчке.
        System.out.println("CHANGE STATUS: Subtask3 NEW->DONE");
        System.out.println("Подзадачи:");
        for (Task t : manager.getSubtasks().values()) {
            System.out.println(t);
        }
        System.out.println("Эпики:");
        for (Epic e : manager.getEpics().values()) {
            System.out.println(e);

        }
        final Epic epic = manager.getEpic(epicId1);
        epic.setStatus("NEW");
        manager.updateEpic(epic);
        System.out.println("CHANGE STATUS: Epic1 IN_PROGRESS->NEW");
        printAllTasks(manager);
        System.out.println("Эпики:");
        for (Task e : manager.getEpics().values()) {
            System.out.println(e);

        }


        // Удаление
        System.out.println("DELETE: Task1");
        manager.deleteTask(taskId1);
        System.out.println("DELETE: Epic1");
        manager.deleteEpic(epicId1);
        printAllTasks(manager);

        manager.removeAllTasks();
    }




    private static void printAllTasks(Manager manager) {
        System.out.println("Задачи:");
        for (Task task : manager.getTasks().values()) {
            System.out.println(task);
        }

        System.out.println("Эпики:");
        for (Task epic : manager.getEpics().values()) {
            System.out.println(epic);

        }


            System.out.println("Подзадачи:");
            for (Task subtask : manager.getSubtasks().values()) {
                System.out.println(subtask);
            }


        }
    }


