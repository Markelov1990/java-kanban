package model;


public class Epic extends Task {



    public Epic(String name, String detail, String a) {
        super(name,
                detail,
                a);
        setStatus(a);
    }




    @Override
    public String toString() {
        String result = "Эпик: " + name + ", Подробности: " + detail + ", Номер задачи: " + id + ", Статус " + status  +" .\n";

        return result;
    }

}