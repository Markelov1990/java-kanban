import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;


public class Manager {
    int identificator=0;
    Scanner scanner = new Scanner(System.in);


    HashMap<Integer, Object> listOfTasksWithId = new HashMap<>();
    private String name;
    private String detail;
    Status status;
    int id;
    Task task1 = new Task(name, detail,id,status);


    public void addNewTask() {

        while (true) {
            System.out.println("Хотите внести задачу или подзадачу? Задача - 1, Подзадача - 2");
            String answer = scanner.nextLine();
            if (answer.equals("1")) {
                identificator++;
                System.out.println("Введите название задачи");
                name = scanner.nextLine();
                System.out.println("Внесите пояснение к задаче");
                detail = scanner.nextLine();
                id = identificator;
                Task task1 = new Task(name, detail,id,Status.NEW);
                listOfTasksWithId.put(identificator,task1);
                System.out.println("Задача внесена с идентефикатором " + id);
                break;

            } else if (answer.equals("2")) {
                ArrayList<Object> listOfSubtasks0 = new ArrayList<>();
                Epic epic0 = new Epic("a", "b", 0, Status.NEW, listOfSubtasks0);


                System.out.println("Введите ИД задачи к которой надо добавить под задачу:");
                int answerForId = scanner.nextInt();
                Task transitTask = null;
                for (int answer1: listOfTasksWithId.keySet()) {
                    if (answer1==answerForId) {
                        transitTask = (Task) listOfTasksWithId.get(answer1);

                    }
                }
                if (transitTask == null) {
                    System.out.println("Такая задача не найдена!");
                    break;
                }
                if (epic0.getClass() == transitTask.getClass()) {
                    scanner.nextLine();
                    System.out.println("Введите название задачи");
                    name = scanner.nextLine();
                    System.out.println("Внесите пояснение к задаче");
                    detail = scanner.nextLine();
                    id = answerForId;
                    SubTask subtask = new SubTask(name, detail, id, Status.NEW, 0);
                    ((Epic) transitTask).getListOfSubtasks().add(subtask);
                    subtask.setIdOfSubtusk(((Epic) transitTask).getListOfSubtasks().indexOf(subtask) + 1);
                    break;

                } else {

                    ArrayList<Object> listOfSubtasks = new ArrayList<>();
                    listOfTasksWithId.remove(answerForId);
                    scanner.nextLine();
                    System.out.println("Введите название задачи");
                    name = scanner.nextLine();
                    System.out.println("Внесите пояснение к задаче");
                    detail = scanner.nextLine();
                    id = answerForId;
                    SubTask subtask = new SubTask(name, detail, id, Status.NEW, 0);
                    listOfSubtasks.add(subtask);
                    subtask.setIdOfSubtusk(listOfSubtasks.indexOf(subtask) + 1);
                    Epic epic1 = new Epic(transitTask.getName(), transitTask.getDetail(), transitTask.getId(), transitTask.getStatus(), listOfSubtasks);
                    listOfTasksWithId.put(answerForId, epic1);

                    break;
                }
            } else {
                System.out.println("Введите 1 или 2");
            }
        }
    }
    public void printAllTask () {

        for (int i = 1; i <= identificator; i++) {
            if (listOfTasksWithId.get(i) != null) {
                System.out.println(listOfTasksWithId.get(i));
            }

        }
    }

    public void removeTask() {

        listOfTasksWithId.clear();
        System.out.println("Список задач пуст");
    }
    public void getById() {
        System.out.println("Введите ИД задачи, которую ищете:");
        int answerForId = scanner.nextInt();
        Task transitTask = null;
        for (int answer: listOfTasksWithId.keySet()) {
            if (answer==answerForId) {
                transitTask = (Task) listOfTasksWithId.get(answer);
                System.out.println("Нашли!");
                System.out.println(transitTask);
            }
        }

    }
    public void removeById() {
        System.out.println("Введите идентификатор задачи, которую надо удалить: ");
        int answerForId = scanner.nextInt();
        for (int answer: listOfTasksWithId.keySet()) {
            if (answer==answerForId) {
                listOfTasksWithId.remove(answerForId);
                System.out.println("Удалили!");
                return;
            }
        }
    }
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

}
