public class SubTask extends Task{

    int idOfSubtusk;


    public SubTask(String name, String detail, int id, Status status, int idOfSubtusk) {

        super(name, detail, id, status);
        this.idOfSubtusk = idOfSubtusk;

    }

    public int getIdOfSubtusk() {
        return idOfSubtusk;
    }

    public void setIdOfSubtusk(int idOfSubtusk) {
        this.idOfSubtusk = idOfSubtusk;
    }

    @Override
    public String toString() {
        String result = "Задача: " + name + ", Подробности: " + detail + ", Номер основной задачи: " + id +" Номер подзадачи: " +  idOfSubtusk +  ", Статус " + status  +" .\n";

        return result;
    }
}