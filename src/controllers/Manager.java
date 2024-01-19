package controllers;


import java.util.ArrayList;
import java.util.HashMap;

import model.Status;
import model.Task;
import model.SubTask;
import model.Epic;


public class Manager {

    protected final HashMap<Integer, Task> tasks = new HashMap<>();
    protected final HashMap<Integer, Epic> epics = new HashMap<>();
    protected final HashMap<Integer, SubTask> subtasks = new HashMap<>();

    private int identificator = 0;


    public int addNewTask(Task task) {
        identificator++;
        task.setId(identificator);
        tasks.put(identificator, task);
        return identificator;


    }

    public int addNewEpic(Epic epic) {
        identificator++;
        epic.setId(identificator);
        epics.put(identificator, epic);
        return identificator;


    }

    public int addNewSubTask(SubTask subtask) {
        identificator++;
        subtask.setId(identificator);
        subtasks.put(identificator, subtask);
        epics.get(subtask.getIdOfEpic()).getListOfSubtasks().add(subtask);
        return identificator;

    }


    public void deleteTasks() {
        tasks.clear();
    }

    public void deleteSubtasks() {
        for (Epic epic : epics.values()) {
            //epic.cleanSubtaskIds();
            /*Вот тут я совершенно не понял, вопрос в чем заключается:
            1) Сейчас у подзадач есть два номера: номер основной задачи и номер эпика, к которому он привязан
            2) Когда подзадача появляется новая ей присваивается номер наравне в другими задачами.
            3) Потом она по номеру эпика попадает в список приложенный к Эпику.
            4) То есть фактически номера своего ИД у неё нет. Есть номер общий - но его не почистить
            5) Есть номер в списке подзадач Эпика, но там обычный список, а не мапа, поэтому номера сами удаляются
            Есть три пути решения как я понимаю, но тут надо прояснить, что надо в итоге:
            1) Создать не интовский список основных идентификаторов, а прям мапу, и если номер освобождается - то
            любая следующая задача/эпик/подзадача попадает в этот номер. Но тогда странно что теряется основная
            нумерация всех поставленных задач
            2) Есть вариант сделать не список подзадач, а Мапу и вводить ключи, которые фактически и будут являтся
            номерами подзадач и тогда у любой подзадачи будет три идентификатора. Номер основной при постановке, номер
            Эпику куда привязано и номер подзадачи внутри задачи. Итого я тогда удалю подзадачу и останется свободный
            слот в Мапе и в него я и добавляю следующую подзадачу. Не знаю, насколько это критично, но тогда тоже
            теряется нумерция по поставленным и удаленным задачам.
            3) Ничего не делаю. Странно звучит, но тем не менее. Сейчас нет нумерции подзадач внутри эпика, они идут
            списком и при удалении списка автоматически номер в списке освобождается. Да и такой нумерации у неё нет.

            Помогите пожалуйста, я только что удалил 200 строк с интерактивом, не очень хочется опять сделать и потом
            узнать, что задача вообще не в этом. Можете просто цифру варианта написать))
             */
            updateEpic(epic);
        }
        subtasks.clear();
    }

    public void deleteEpics() {
        epics.clear();
        subtasks.clear();
    }


    public Task getTask(int id) {
        return tasks.get(id);
    }

    public SubTask getSubtask(int id) {

        return subtasks.get(id);
    }

    public Epic getEpic(int id) {

        return epics.get(id);
    }

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

    public void updateEpic(Epic epic) {
        Status status1 = Status.valueOf("DONE");
        int b = epic.getId() + 1;
        int count = 0;
        for (SubTask t : subtasks.values()) {
            if (t.getIdOfEpic() == epic.getId()) {
                count++;
            }
        }

        int countOfDone = 0;
        for (SubTask t : subtasks.values()) {
            if (t.getStatus().equals(status1)) {
                countOfDone++;
            }
        }

        if (count == countOfDone) {
            epic.setStatus("DONE");
        }
    }



    public ArrayList<Task> getTasks() {
        return new ArrayList<>(tasks.values());
    }

    public ArrayList<Epic> getEpics() {
        return new ArrayList<>(epics.values());
    }

    public ArrayList<SubTask> getSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    public void deleteTask(int id) {

        tasks.remove(id);

    }

    public void deleteEpic(int id) {
        final Epic epic = epics.remove(id);
        //Немного иначе сделал, может всё же подойдет?
        for (SubTask subTask : subtasks.values()) {
            if (subTask.getIdOfEpic() == id) {
                subTask.getId();
                subtasks.remove(subTask.getId());
            };
        }
    }

    public void deleteSubtask(int id) {
        SubTask subtask = subtasks.remove(id);
        if (subtask == null) {
            return;
        }
        Epic epic = epics.get(subtask.getIdOfEpic());
        epic.getListOfSubtasks().remove(id);
        updateEpic(epic);
    }
}
