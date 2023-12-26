public class Task {

    String name;
    String detail;
    int id;
    Status status;
    public Task(String name, String detail, int id, Status status) {
        this.name = name;
        this.detail = detail;
        this.id = id;
        this.status = status;

    }



    public String getName() {
        return name;
    }

    public String getDetail() {
        return detail;
    }

    public int getId() {
        return id;
    }

    public Status getStatus() {
        return status;
    }

    public Status setStatus(String answer, Task task) {
        switch (answer) {
            case "NEW":
                task.status = Status.NEW;

                break;
            case "INPROGRESS":
                task.status = Status.INPROGRESS;
                return task.status;

            case "DONE":
                task.status = Status.DONE;

                break;
            default:
                System.out.println("Неверный статус");
        }
        return task.status;
    }

    @Override
    public String toString() {
        String result = "Задача: " + name + ", Подробности: " + detail + ", Номер задачи: " + id + ", Статус " + status  +" .\n";

        return result;
    }





}
