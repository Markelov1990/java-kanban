import java.util.Scanner;

public class Main {
    /* К сожалению не видел, как оставить комментарий к самой задаче, поэтому напишу здесь.
    Я отправляю код и по тому как я понял должна работать програма - она работает.
    Но боюсь, что что-то неправильно понял. А если приводить чистить некоторые моменты, а потом окажется, что вообще неправильно понял - будет обидно.
    Напишите пожалуйтса - основной код в верном ключе или нет.

     */
    public static void main(String[] args) {

        Manager manager = new Manager();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            printMenu();
            String command = scanner.nextLine();

            switch (command) {
                case "1":
                    manager.printAllTask();
                    break;
                case "2":
                    manager.removeTask();
                    break;
                case "3":
                    manager.getById();
                    break;
                case "4":
                    manager.addNewTask();
                    break;
                case "5":
                    manager.changeTask();
                    break;
                case "6":
                    manager.removeById();
                    break;
                case "7":
                    manager.getEpicSubTasks();
                    break;
                case "8":
                    manager.changeStatus();
                    break;

                case "9":
                    return;
            }
        }





    }
    public static void printMenu() {
        System.out.println("Вас приветсвует трекер задач");
        System.out.println("Что хотите сделать?");
        System.out.println("1 - Получить список всех задач");
        System.out.println("2 - Удалить все задачи");
        System.out.println("3 - Получить задачу по идентификатору");
        System.out.println("4 - Создать задачу");
        System.out.println("5 - Обновить задачу");
        System.out.println("6 - Удалить задачу по ИД");
        System.out.println("7 - Получить список подзадача Эпика");
        System.out.println("8 - Изменить статус задачи");
        System.out.println("9 - Выход");
    }
}
