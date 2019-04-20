package utility;

/**
 * Класс инкапсулирует справочное руководство по доступным командам в консольном дебаг-трекере.
 * Суть методов интуитивно понятна по наименованию методов.
 *
 * @author Mishanin Aleksey
 * */
public class Manual {

    public static void aboutAddProject(){
        System.out.println("Использование: addProject [nameProject]\n" +
                "Позволяет добавить новый проект в базу данных.\n" +
                "Аргументы: \n" +
                "nameProject\t\tНаименование нового проекта\n");
    }

    public static void aboutAddIssue(){
        System.out.println("Использование: addIssue [nameIssue] [nameProject] [nameUser]\n" +
                "Позволяет закрепить новую задачу в рамках проекта за выбранным пользователем.\n" +
                "Аргументы: \n" +
                "nameIssue\t\tНаименование новой задачи\n" +
                "nameProject\t\tНаименование проекта. Проект должен быть добавлен в БД\n" +
                "nameUser\t\tНаименование пользователя. Пользователь должнен быть добавлен в БД\n");
    }

    public static void aboutAddUser(){
        System.out.println("Использование: addUser [nameUser]\n" +
                "Позволяет добавить нового пользователя в базу данных.\n" +
                "Аргументы: \n" +
                "nameUser\t\tНаименование нового пользователя\n");
    }

    public static void aboutReportAll(){
        System.out.println("Использование: reportAll [essence]\n" +
                "Позволяет выгрузить список объектов выбранной сущности.\n" +
                "Аргументы: \n" +
                "essence\t\tТип выбранной сущности: или \"project\", или \"users\", или \"issue\"\n");
    }

    public static void aboutReportSome(){
        System.out.println("Использование: reportSome [nameProject] [nameUser]\n" +
                "Позволяет выгрузить список задач, закрепленных в рамках проекта за выбранным пользователем.\n" +
                "Аргументы: \n" +
                "nameProject\t\tНаименование проекта в рамках которого осуществляется поиск задач\n" +
                "nameUser\t\tНаименование пользьвателя за которым закреплена задача\n");
    }

    public static void aboutHelp(){
        System.out.println("Использование: [nameCommand] --help\n" +
                "Позволяет получить справку по выбранной команде.\n");
    }
}
