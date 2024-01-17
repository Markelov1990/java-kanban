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

    public int getIdOfEpic() {
        return idOfEpic;
    }



    @Override
    public String toString() {
        String result = "Подзадача: " + name + ", Подробности: " + detail + ", Номер основной задачи: " + idOfEpic +" Номер подзадачи: " +  id +  ", Статус " + status  +" .\n";

        return result;
    }
}