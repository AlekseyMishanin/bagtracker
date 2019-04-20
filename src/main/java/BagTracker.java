import exceptions.IssueAlreadyExistsException;
import exceptions.ProjectAlreadyExistsException;
import exceptions.UserAlreadyExistsException;
import lombok.NonNull;
import model.Issue;
import model.Project;
import model.User;
import org.apache.log4j.Logger;
import utility.Initial;
import utility.Manual;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
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
        if(Initial.isOk) {
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
                } catch (IOException |
                        ProjectAlreadyExistsException |
                        UserAlreadyExistsException |
                        IssueAlreadyExistsException e) {
                    log.error(e.getMessage());
                    e.getMessage();
                }
            }
        } else {
            log.error("Error of creation of a directory structure.");
        }
    }

    /**
     * Реализация метода интерфейса Command. Метод добавляет в базу данных новый проект.
     *
     * @param   project
     *          объект нового проекта
     *
     * @throws  ProjectAlreadyExistsException
     *          объект проекта уже существует в базе данных
     * */
    @Override
    public void addProject(@NonNull Project project) throws ProjectAlreadyExistsException, IOException {
        //если проект существует в БД
        if(project.exists()){
            //генерируем исключительную ситуацию
            throw new ProjectAlreadyExistsException(project.getName());
            //иначе
        } else {
            //добавляем новый проект в БД
            project.writeToFile();
            //фиксируем событие в логах
            log.info("Use command addProject with arguments: " + project.getName());
        }
    }
    /**
     * Реализация метода интерфейса Command. Метод добавляет в базу данных новую задачу.
     *
     * @param   issue
     *          объект новой задачи
     *
     * @throws  IssueAlreadyExistsException
     *          объект задачи уже существует в базе данных
     * */
    @Override
    public void addIssue(@NonNull Issue issue) throws IssueAlreadyExistsException, IOException {
        //если задача существует в БД
        if(issue.exists()){
            //генерируем исключительную ситуацию
            throw new IssueAlreadyExistsException(issue.getName());
            //иначе
        } else {
            //добавляем новую задачу в БД
            issue.writeToFile();
            //фиксируем событие в логах
            log.info("Use command addIssue with arguments: " + issue.getName());
        }
    }

    /**
     * Реализация метода интерфейса Command. Метод добавляет в базу данных нового пользователя.
     *
     * @param   user
     *          объект нового пользователя
     *
     * @throws  UserAlreadyExistsException
     *          объект пользователя уже существует в базе данных
     * */
    @Override
    public void addUser(@NonNull User user) throws IOException, UserAlreadyExistsException {
        //если пользователь существует в БД
        if(user.exists()){
            //генерируем исключительную ситуацию
            throw new UserAlreadyExistsException(user.getName());
            //иначе
        } else {
            //добавляем пользователя в БД
            user.writeToFile();
            //фиксируем собыите в логах
            log.info("Use command addUser with arguments: " + user.getName());
        }
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
    public void reportSome(@NonNull Project project, @NonNull User user) throws IOException {
        //проверяем сущуствуют ли в БД выбранный проект и пользователь
        if(project.exists() || user.exists()) {
            System.out.println("В рамках " + project.getName() + " пользователь " + user.getName() +" завел следующие задачи: ");
            //используя метод lines() NIO получаем Stream. Далее фильтрами строим нужный список.
            Files.lines(Paths.get("bagtracker","issues.txt"))       //открываем выставленные задачи
                    .filter((n)->n.indexOf(project.getName())!=-1)              //ищем совпадение по проекту
                    .filter((n)->n.indexOf(user.getName())!=-1)                 //ищем совпадение по пользователю
                    .forEach((n)-> System.out.println(n));                      //результат выводим на стандартное устройство вывода
            System.out.println();
            //фиксируем событие в логах
            log.info("Use command reportSome with arguments: " + project.getName() + ", " + user.getName());
        } else {
            System.out.println("Указаны не все аргументы или указанные аргументы не существуют в БД. Справка по команде: [команда] --help ");
        }
    }

    /**
     * Реализация метода интерфейса Command. Метод выгружает список в соответствии с параметрами.
     *
     * @param   essence
     *          наименование сущности в рамках которой нужно получить отчет
     * */
    @Override
    public void reportAll(@NonNull String essence) throws IOException {
        log.info("Use command reportAll with arguments: " + essence);
        switch (essence){
            case "project":
                Files.lines(Paths.get("bagtracker","project.txt")).forEach((n)-> System.out.println(n));
                System.out.println();
                break;
            case "users":
                Files.lines(Paths.get("bagtracker","users.txt")).forEach((n)-> System.out.println(n));
                System.out.println();
                break;
            case "issue":
                Files.lines(Paths.get("bagtracker","issues.txt")).forEach((n)-> System.out.println(n));
                System.out.println();
                break;
            default:
                System.out.println("Неизвестный аргумент \"" + essence + "\". Справка по команде: [команда] --help ");
                System.out.println();
                break;
        }
    }
}
