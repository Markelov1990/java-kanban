package model;
import java.util.ArrayList;

public class Epic extends Task {

    private ArrayList<Object> listOfSubtasks = new ArrayList<>();

    public Epic(String name, String detail, String a) {
        super(name,
                detail,
                a);
        setStatus(a);

    }

    public ArrayList<Object> getListOfSubtasks() {
        return listOfSubtasks;
    }

    public void setListOfSubtasks(ArrayList<Object> listOfSubtasks) {
        this.listOfSubtasks = listOfSubtasks;
    }


    @Override
    public String toString() {
        String result = "Эпик: " + name + ", Подробности: " + detail + ", Номер задачи: " + id + ", Статус " + status  +" .\n"
                + "Подзадачи: \n" + listOfSubtasks;;

        return result;
    }

}