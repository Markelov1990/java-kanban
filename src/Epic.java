import java.util.ArrayList;

public class Epic extends Task{
    ArrayList<Object> listOfSubtasks = new ArrayList<>();


    public Epic(String name, String detail, int id, Status status, ArrayList<Object> listOfSubtasks) {
        super(name, detail, id, status);
        this.listOfSubtasks = listOfSubtasks;
    }

    public ArrayList<Object> getListOfSubtasks() {
        return listOfSubtasks;
    }

    public void setListOfSubtasks(ArrayList<Object> listOfSubtasks) {
        this.listOfSubtasks = listOfSubtasks;
    }

    @Override
    public String toString() {
        String result = "Задача: " + name + ", Подробности: " + detail + ", Номер задачи: " + id + ", Статус " + status  +" .\n"
                + "Подзадачи: \n" + listOfSubtasks;

        return result;
    }

}