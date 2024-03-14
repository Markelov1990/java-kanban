package model;
import java.time.Instant;
import java.util.ArrayList;

public class Epic extends Task {

    private final ArrayList<Object> listOfSubtasks = new ArrayList<>();
    private final Instant endTime;

    public Epic(String name, String detail, Instant startTime, long duration) {
        super(name,
                detail, startTime, duration);
        this.status = Status.NEW;
        this.endTime = super.getEndTime();

    }

    public ArrayList<Object> getListOfSubtasks() {
        return listOfSubtasks;
    }

    public void cleanSubtaskIds() {
        listOfSubtasks.clear();
    }



    @Override
    public Instant getEndTime() {
        return endTime;
    }

    @Override
    public String toString() {
        return "Эпик: " + name + ", Подробности: " + detail + ", Номер задачи: " + id + ", Статус " + status  + ", Начало задачи: " + startTime.toEpochMilli() + " Длительность задачи: " + duration + " .\n"
                + "Подзадачи: \n" + listOfSubtasks;

    }

}