package model;

import java.time.Instant;

public class Task {

    protected String name;
    protected String detail;
    protected int id;
    protected Status status;
    protected Instant startTime;
    protected long duration;

    public Task(String name, String detail, Status status, Instant startTime, long duration) {
        this.name = name;
        this.detail = detail;
        this.status = status;
        this.startTime = startTime;
        this.duration = duration;


    }

    public Task(String name, String detail, Instant startTime, long duration) {
        this.name = name;
        this.detail = detail;
        this.startTime = startTime;
        this.duration = duration;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getDetail() {
        return detail;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public long getDuration() {
        return duration;
    }
    public Instant getEndTime() {

        return startTime.plusSeconds(duration * 60);
    }

    public int getId() {
        return id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(String answer) {
        switch (answer) {
            case "NEW":
                status = Status.NEW;

                break;
            case "IN_PROGRESS":
                status = Status.INPROGRESS;
                break;

            case "DONE":
                status = Status.DONE;

                break;
            default:
        }

    }

    @Override
    public String toString() {
        return "Задача: " + name + ", Подробности: " + detail + ", Номер задачи: " + id + ", Статус " + status  + ", Начало задачи: " + startTime.toEpochMilli() + " Длительность задачи: " + duration +   ".\n";

    }





}
