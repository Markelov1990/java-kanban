package controllers;


import java.util.HashMap;

import model.Status;
import model.Task;
import model.SubTask;
import model.Epic;


public class Manager {

    protected final HashMap<Integer, Task> tasks = new HashMap<>();
    protected final HashMap<Integer, Epic> epics = new HashMap<>();
    protected final HashMap<Integer, SubTask> subtasks = new HashMap<>();

    int identificator = 0;
    private String name;
    private String detail;
    Status status;
    int id;



    public int addNewTask(Task task) {
        identificator++;
        task.setId(identificator);
                tasks.put(identificator,task);
                System.out.println("Задача внесена с идентефикатором " + identificator);
                return identificator;

    }

    public int addNewEpic(Epic epic) {
        identificator++;
        epic.setId(identificator);
        epics.put(identificator,epic);
        System.out.println("Эпик внесен с идентефикатором " + identificator);
        return identificator;

    }

    public int addNewSubTask(SubTask subtask) {
        identificator++;
        subtask.setId(identificator);
        subtasks.put(identificator,subtask);
        System.out.println("Подзадача внесена с идентефикатором " + identificator);
        return identificator;

    }



    public void removeAllTasks() {

        tasks.clear();
        subtasks.clear();
        epics.clear();

    }



    public Task getTask(int id) {
        Task transitTask = null;
        for (int a: tasks.keySet()) {
            if (a == id) {
                transitTask = tasks.get(id);
            }
        }
        return transitTask;
    }

    public SubTask getSubtask(int id) {
        SubTask transitSubtask = null;
        for (int a: subtasks.keySet()) {
            if (a == id) {
                transitSubtask = subtasks.get(id);
            }
        }
        return transitSubtask;
    }

    public Epic getEpic(int id) {
        Epic transitEpic = null;
        for (int a: epics.keySet()) {
            if (a == id) {
                transitEpic = epics.get(id);
            }
        }
        return transitEpic;
    }

    public SubTask getEpicSubtasks (int idOfEpic) {
        SubTask transitSubtask = null;
        for (int a: subtasks.keySet()) {
            if (idOfEpic == subtasks.get(a).getIdOfEpic()) {
                transitSubtask = subtasks.get(a);
                return transitSubtask;
            }

        }
        return null;
    }

    public void updateEpic (Epic epic) {
        Status status1 = Status.valueOf("DONE");
            int b = epic.getId();
            int count = 0;
            for (SubTask t : subtasks.values()) {
                if (t.getIdOfEpic() == epic.getId()) {
                    count++;
                }
            }
        System.out.println(count);
        int countOfDone = 0;
        for (SubTask t : subtasks.values()) {
            if (t.getStatus().equals("DONE")) {
                countOfDone++;
            }
        }
        System.out.println(countOfDone);
        if (count == countOfDone) {
            epic.setStatus("DONE");
        }
        }


    public HashMap<Integer, Task> getTasks() {

        return tasks;
    }

    public HashMap<Integer, Epic> getEpics() {

        return epics;
    }

    public HashMap<Integer, SubTask> getSubtasks() {

        return subtasks;
    }

    public void deleteTask(int id) {
        tasks.remove(id);
    }

    public void deleteEpic(int id) {
        epics.remove(id);
    }


/*
    public void changeTask() {
        ArrayList<Object> listOfSubtasks0 = new ArrayList<>();
        Epic epic0 = new Epic("a", "b", 0, Status.NEW, listOfSubtasks0);
        Task task = new Task("a", "a", 0,Status.NEW);
        System.out.println("Введите идентификатор задачи, которую хотите изменить: ");
        int answerForId = scanner.nextInt();
        for (int answer: listOfTasksWithId.keySet()) {
            if (answer==answerForId) {
                if(listOfTasksWithId.get(answer).getClass() == task.getClass()) {
                    scanner.nextLine();
                    System.out.println("Введите название задачи");
                    name = scanner.nextLine();
                    System.out.println("Внесите пояснение к задаче");
                    detail = scanner.nextLine();
                    id = answer;
                    Task task1 = new Task(name, detail,id,Status.NEW);
                    listOfTasksWithId.put(id,task1);
                    System.out.println("Задача перезаписана с идентефикатором " + id);
                    break;
                } else {
                    epic0 = (Epic) listOfTasksWithId.get(answer);
                    scanner.nextLine();
                    System.out.println("Введите название задачи");
                    name = scanner.nextLine();
                    System.out.println("Внесите пояснение к задаче");
                    detail = scanner.nextLine();
                    id = answer;
                    Epic epic1 = new Epic(name, detail, answer, Status.NEW, epic0.getListOfSubtasks());
                    listOfTasksWithId.put(answerForId, epic1);
                    System.out.println("Задача перезаписана с идентефикатором " + id);
                    //изменение подзадачи
                    break;
                }
            }
        }


    }
    public void getEpicSubTasks() {
        System.out.println("Введите ИД задачи, подзадачи, которой ищете:");
        int answerForId = scanner.nextInt();
        ArrayList<Object> listOfSubtasks0 = new ArrayList<>();
        Epic epic0 = new Epic("a", "b", 0, Status.NEW, listOfSubtasks0);
        for (int answer: listOfTasksWithId.keySet()) {
            if (answer==answerForId) {
                System.out.println("Подзадачи");
                epic0 = (Epic) listOfTasksWithId.get(answer);
                System.out.println(epic0.getListOfSubtasks());

            }
        }

    }
    public void changeStatus() {

        ArrayList<Object> listOfSubtasks0 = new ArrayList<>();
        Epic epic0 = new Epic("a", "b", 0, Status.NEW, listOfSubtasks0);
        Task task = new Task("a", "a", 0,Status.NEW);
        System.out.println("Введите идентификатор задачи, статус которой хотите изменить: ");
        int answerForId = scanner.nextInt();
        for (int answer: listOfTasksWithId.keySet()) {
            if (answer==answerForId) {
                if(listOfTasksWithId.get(answer).getClass() == task.getClass()) {
                    scanner.nextLine();
                    task = (Task) listOfTasksWithId.get(answer);
                    System.out.println("Внесите статус к задаче: NEW; INPROGRESS; DONE");
                    status = task.setStatus(scanner.nextLine(), task);
                    break;
                } else {
                    epic0 = (Epic) listOfTasksWithId.get(answer);
                    System.out.println("У этой задачи, есть подзадачи.");
                    for (int answer1: listOfTasksWithId.keySet()) {
                        if (answer1==answerForId) {
                            System.out.println("Подзадачи");
                            epic0 = (Epic) listOfTasksWithId.get(answer);
                            System.out.println(epic0.getListOfSubtasks());
                        }
                    }
                    System.out.println("Введите идентификатор подзадачи, статус которой хотите изменить: ");
                    int answerForIdSubtusak = scanner.nextInt();
                    listOfSubtasks0 = epic0.getListOfSubtasks();
                    for (int i = 0; i < listOfSubtasks0.size(); i++) {
                        SubTask subTask = (SubTask) listOfSubtasks0.get(i);
                        int idAnswer = subTask.getIdOfSubtusk();
                        if (answerForIdSubtusak == idAnswer) {
                            scanner.nextLine();
                            System.out.println("Внесите статус к задаче: NEW; INPROGRESS; DONE");
                            status = subTask.setStatus(scanner.nextLine(), subTask);
                            listOfSubtasks0.remove(i);
                            listOfSubtasks0.add(i, subTask);
                            epic0.setListOfSubtasks(listOfSubtasks0);
                            listOfTasksWithId.put(answerForId, epic0);
                            break;
                        }
                    }
                    Status status1 = Status.DONE;
                    int count = 0;
                    for (int i = 0; i < listOfSubtasks0.size(); i++) {
                        SubTask subTask = (SubTask) listOfSubtasks0.get(i);
                        if (status1.equals(subTask.getStatus())) {
                            count++;
                        }
                    }

                    if (count == listOfSubtasks0.size()) {
                        epic0.setStatus("DONE", epic0);
                        listOfTasksWithId.put(answerForId, epic0);
                    }
                }




                break;
            }
        }
    }
*/
}
