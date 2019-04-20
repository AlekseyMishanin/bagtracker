import lombok.NonNull;
import model.Issue;
import model.Project;
import model.User;
import org.apache.log4j.Logger;
import utility.DBServise;
import utility.Manual;

import java.sql.SQLException;
import java.util.Scanner;

/**
 *  Класс содержит основную логику работы консольного дебаг-трекера.
 *
 * @author Mishanin Aleksey
 * */
public class BagTracker implements Command{

    private static final Logger log = Logger.getLogger(BagTracker.class);                   //логгер для сообщений
    private static final String welcome = "Вас приветствует консольный дебаг-трекер!\n\n"+  //переменная для приветствия
                    "Список команд: \n" +
                    "addProject \n" +
                    "addIssue \n" +
                    "addUser \n" +
                    "reportAll \n" +
                    "reportSome\n" +
                    "help\n" +
                    "exit\n\n";


    /**
     * Метод в цикле ожидает от пользователя ввода новой команды. Если комманда введена корретно, она будет выполнения.
     * В случае появления исключительных ситуаций данные вносятся в логер.
     * */
    public void start() {
        if(!DBServise.connect()) return;
        System.out.println(welcome);                            //выводим приветствие на стандартное устройство вывода
        Scanner scanner = new Scanner(System.in);               //создаем объект сканера для стандартного устройства ввода
        while(scanner.hasNext()){
            String command = scanner.nextLine();                //читаем введенную пользователем строку
            String[] listStr = command.split(" ");       //разбиваем строку на массив
            try{
                switch (listStr[0]){                            //проверяем введенную команду
                    case "exit":
                        System.out.println("Bye");
                        return;                                 //выходим из цикла и завершаем работу программы
                    case "help":
                        Manual.aboutHelp();                     //выводим общую справку
                        break;
                    case "addProject":
                        if(listStr.length == 1 || listStr[1].equals("--help")){ //если введена команда без аргументов или с ключем --help
                            Manual.aboutAddProject();                           //выводим справку по команде
                        } else {
                            addProject(new Project(listStr[1]));                //в противном случае выполняем команду
                        }
                        break;
                    case "addIssue":
                        if(listStr.length == 1 ||  listStr[1].equals("--help")){
                            Manual.aboutAddIssue();
                        } else {
                            Project project = new Project(listStr[2]);
                            User user = new User(listStr[3]);
                            addIssue(new Issue(listStr[1], project, user));
                        }
                        break;
                    case "addUser":
                        if(listStr.length == 1 ||  listStr[1].equals("--help")){
                            Manual.aboutAddUser();
                        } else {
                            addUser(new User(listStr[1]));
                        }
                        break;
                    case "reportAll":
                        if(listStr.length == 1 ||  listStr[1].equals("--help")){
                            Manual.aboutReportAll();
                        } else {
                            reportAll(listStr[1]);
                        }
                        break;
                    case "reportSome":
                        if(listStr.length == 1 ||  listStr[1].equals("--help")){
                            Manual.aboutReportSome();
                        } else {
                            reportSome(new Project(listStr[1]), new User(listStr[2]));
                        }
                        break;
                    default:
                        System.out.println("Неизвестная команда \n" + listStr[0] + "\n");
                }
            } catch (SQLException e) {
                log.error(e.getMessage());
                e.getMessage();
            }
        }
        DBServise.disconect();
    }

    /**
     * Реализация метода интерфейса Command. Метод добавляет в базу данных новый проект.
     *
     * @param   project
     *          объект нового проекта
     *
     * */
    @Override
    public void addProject(@NonNull Project project) {
        project.writeToDB();
    }
    /**
     * Реализация метода интерфейса Command. Метод добавляет в базу данных новую задачу.
     *
     * @param   issue
     *          объект новой задачи
     * */
    @Override
    public void addIssue(@NonNull Issue issue) {
        issue.writeToDB();
    }

    /**
     * Реализация метода интерфейса Command. Метод добавляет в базу данных нового пользователя.
     *
     * @param   user
     *          объект нового пользователя
     * */
    @Override
    public void addUser(@NonNull User user) {
        user.writeToDB();
    }

    /**
     * Реализация метода интерфейса Command. Метод выгружает список в соответствии с параметрами.
     *
     * @param   project
     *          проект в рамках которого осуществляется выгрузка
     *
     * @param   user
     *          пользователь в рамках которого осуществляется выгрузка
     * */
    @Override
    public void reportSome(@NonNull Project project, @NonNull User user) throws SQLException {
        DBServise.reportSome(project, user);
    }

    /**
     * Реализация метода интерфейса Command. Метод выгружает список в соответствии с параметрами.
     *
     * @param   essence
     *          наименование сущности в рамках которой нужно получить отчет
     * */
    @Override
    public void reportAll(@NonNull String essence) throws SQLException {
        log.info("Use command reportAll with arguments: " + essence);
        DBServise.reportAll(essence);
    }
}
