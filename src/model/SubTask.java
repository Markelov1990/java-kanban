package model;

public class SubTask extends Task {

    private int idOfEpic;


    public SubTask(String name, String detail, String a, int idOfEpic) {

        super(name,
                detail,
                a);
        setStatus(a);
        this.idOfEpic = idOfEpic;
    }

    public int getIdOfSubtusk() {
        return idOfEpic;
    }

    public void setIdOfSubtusk(int idOfEpic) {
        this.idOfEpic = idOfEpic;
    }

    @Override
    public String toString() {
        String result = "Задача: " + name + ", Подробности: " + detail + ", Номер основной задачи: " + idOfEpic +" Номер подзадачи: " +  id +  ", Статус " + status  +" .\n";

        return result;
    }
}