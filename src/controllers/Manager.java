package controllers;


import java.util.ArrayList;
import java.util.HashMap;

import model.Status;
import model.Task;
import model.SubTask;
import model.Epic;
/*
Все что нужно поправил. По ТЗ:
  Получить список всех задач - есть
  Удалить все задачи - есть в три отдельных метода для задач, подзадач и эпиков
   Получить задачу по идентификатору - есть, для эпиков выдает сразу подзадачи
    Создать задачу - есть записывает в три отдельных списка
    Обновить задачу - есть, у Эпика меняется при изменении статуса подзадачи
    Удалить задачу по ИД - есть, у эпиков удаляются также подзадачи, по их номерам мы вроде определили, что номер
      только общий его удалять не надо
    Получить список подзадач Эпика - есть и отдельный метод и печатаеся при выведении всех задач к Эпику
     Изменить статус задачи - есть статусы меняются и у Задач, и у Подзадач, и у Эпика через подзадачи.
 */

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
            epic.cleanSubtaskIds(); //Как и писал до этого определнных ИД нет и их списка тоже, потому сделал метод по удалению всех подзадач в эпике.
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
        Status status1 = Status.DONE;
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


        if (count < countOfDone) {
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

        for (SubTask subTask : subtasks.values()) {
            if (subTask.getIdOfEpic() == id) {
                subtasks.remove(subTask.getId());
            }
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
