package model;

import java.time.Instant;

public class SubTask extends Task {

    private int idOfEpic;


    public SubTask(String name, String detail, Status status, int idOfEpic, Instant startTime, long duration) {

        super(name,
                detail,
                status, startTime, duration);

        this.idOfEpic = idOfEpic;
    }

    public int getIdOfEpic() {
        return idOfEpic;
    }



    @Override
    public String toString() {
        String result = "Подзадача: " + name + ", Подробности: " + detail + ", Номер основной задачи: " + idOfEpic +" Номер подзадачи: " +  id +  ", Статус " + status  + ", Начало задачи: " + startTime.toEpochMilli() + " Длительность задачи: " + duration + " .\n";

        return result;
    }
}