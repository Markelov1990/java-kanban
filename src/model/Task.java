package model;

public class Task {

    protected String name;
    protected String detail;
    protected int id;
    protected Status status;

    public Task(String name, String detail, Status status) {
        this.name = name;
        this.detail = detail;
        this.status = status;
        //setStatus(a);

    }

    public Task(String name, String detail) {
        this.name = name;
        this.detail = detail;
    }

    public void setId(int id) {
        this.id = id;
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
        String result = "Задача: " + name + ", Подробности: " + detail + ", Номер задачи: " + id + ", Статус " + status  +" .\n";

        return result;
    }





}
