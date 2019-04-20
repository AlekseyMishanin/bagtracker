package utility;

import model.Issue;
import model.Project;
import model.User;
import org.apache.log4j.Logger;
import java.sql.*;

/**
 * Класс инкапсулирует работу с SQLite
 *
 * @author Mishanin Aleksey
 * */
public class DBServise {

    private static Connection connection;                                   //соединение с БД
    private static Statement statement;                                     //переменная для запросов к БД
    private static final Logger log = Logger.getLogger(DBServise.class);  //логгер для вывода ошибок


    /**
     * Метод загружает JDBC драйвер, устанавливает соединение с БД.
     * */
    public static boolean connect(){
        try{
            //загрузаем драйвер
            Class.forName("org.sqlite.JDBC");
            //устанавливаем соединение
            connection = DriverManager.getConnection("jdbc:sqlite:test.db");
            statement = connection.createStatement();
            //создаем таблицы (при необходимости)
            return createTable();
        } catch (ClassNotFoundException|SQLException e) {
            log.error(e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Метод закрывает соединение с БД
     * */
    public static void disconect() {
        try {
            connection.close();
            connection = null;
        } catch (SQLException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Метод создает в БД таблицы sers, issue, project, если данные таблицы отсутствуют в БД
     * */
    private static boolean createTable(){
        String sqlCreateUsers = "CREATE TABLE IF NOT EXISTS users (\n" +
                "    id   INTEGER       PRIMARY KEY AUTOINCREMENT\n" +
                "                       NOT NULL\n" +
                "                       UNIQUE,\n" +
                "    name VARCHAR (100) NOT NULL\n" +
                ");";
        String sqlCreateProject = "CREATE TABLE IF NOT EXISTS project (\n" +
                "    id   INTEGER       PRIMARY KEY AUTOINCREMENT\n" +
                "                       UNIQUE\n" +
                "                       NOT NULL,\n" +
                "    name VARCHAR (100) NOT NULL\n" +
                ");\n";
        String sqlCreateIssue = "CREATE TABLE IF NOT EXISTS issue (\n" +
                "    id        INTEGER       PRIMARY KEY AUTOINCREMENT\n" +
                "                            UNIQUE\n" +
                "                            NOT NULL,\n" +
                "    name      VARCHAR (100) NOT NULL,\n" +
                "    iduser    INTEGER       REFERENCES users (id) ON DELETE CASCADE\n" +
                "                                                  ON UPDATE CASCADE,\n" +
                "    idproject INTEGER       REFERENCES project (id) ON DELETE CASCADE\n" +
                "                                                    ON UPDATE CASCADE\n" +
                ");";
        try {
            statement.executeUpdate(sqlCreateUsers);
            statement.executeUpdate(sqlCreateProject);
            statement.executeUpdate(sqlCreateIssue);
            return true;
        } catch (SQLException e) {
            log.error(e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Метод добавляет пользователя в БД
     *
     * @param   user
     *          объект пользователь
     * */
    public static boolean insertUser(User user){
        try {
            //проверяем не существует ли данный пользователь в БД.
            if(getIDUser(user) == -1){
                //составляем текс sql-запроса
                String sql  = String.format("INSERT INTO users(name) VALUES(\"%s\");",user.getName());
                //отправляем запрос БД на исполнение
                statement.executeUpdate(sql);
                //заносим информацию о собщии в журнал
                log.info("User \"" + user.getName() + "\" successfully added");
                System.out.println("User \"" + user.getName() + "\" successfully added");
                return true;
            } else {
                log.info("User \"" + user.getName() + "\" already exist");
                System.out.println("User \"" + user.getName() + "\" already exist");
                return false;
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Метод добавляет объект типа проект в БД
     *
     * @param   project
     *          объект проект
     * */
    public static boolean insertProject(Project project){
        try {
            //проверяем не существует ли данный проект в БД.
            if(getIDProject(project) == -1){
                //составляем текс sql-запроса
                String sql  = String.format("INSERT INTO project(name) VALUES(\"%s\");",project.getName());
                //отправляем запрос БД на исполнение
                statement.executeUpdate(sql);
                //заносим информацию о собщии в журнал
                log.info("Project \"" + project.getName() + "\"successfully added");
                System.out.println("Project \"" + project.getName() + "\" successfully added");
                return true;
            } else {
                log.info("Project \"" + project.getName() + "\"already exist");
                System.out.println("Project \"" + project.getName() + "\"already exist");
                return false;
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Метод добавляет объект  типа задача в БД
     *
     * @param   issue
     *          объект задача
     * */
    public static boolean insertIssue(Issue issue){
        try {
            //проверяем не существует ли данная задача в БД.
            if(getIDIssuet(issue) == -1){
                //составляем текс sql-запроса
                String sql  = String.format("INSERT INTO issue(name, iduser, idproject) VALUES(\"%s\", %d, %d);",issue.getName(),issue.getIduser(),issue.getIdproject());
                //отправляем запрос БД на исполнение
                statement.executeUpdate(sql);
                //заносим информацию о собщии в журнал
                log.info("Issue \"" + issue.getName() + "\"successfully added");
                System.out.println("Issue \"" + issue.getName() + "\" successfully added");
                return true;
            } else {
                log.info("Issue \"" + issue.getName() + "\"already exist");
                System.out.println("Issue \"" + issue.getName() + "\"already exist");
                return false;
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Метод извлекает из БД id пользователя
     *
     * @param   user
     *          объект пользователь
     * */
    public static int getIDUser(User user) throws SQLException {
        String sql  = String.format("SELECT id FROM users WHERE name = \"%s\" ;",user.getName());
        ResultSet res = statement.executeQuery(sql);
        if(res.next()){
            return res.getInt(1);
        } else {
            return -1;
        }
    }

    /**
     * Метод извлекает из БД id проекта
     *
     * @param   project
     *          объект проект
     * */
    public static int getIDProject(Project project) throws SQLException{
        String sql  = String.format("SELECT id FROM project WHERE name = \"%s\" ;",project.getName());
        ResultSet res = statement.executeQuery(sql);
        if(res.next()){
            return res.getInt(1);
        } else {
            return -1;
        }
    }

    /**
     * Метод извлекает из БД id задачи
     *
     * @param   issue
     *          объект задача
     * */
    public static int getIDIssuet(Issue issue) throws SQLException{
        String sql  = String.format("SELECT id FROM issue WHERE name = \"%s\" AND iduser = %d AND idproject = %d ;",issue.getName(),issue.getIduser(),issue.getIdproject());
        ResultSet res = statement.executeQuery(sql);
        if(res.next()){
            return res.getInt(1);
        } else {
            return -1;
        }
    }

    /**
     * Метод извлекает из БД весь список объектов выбранной сущности
     *
     * @param   value
     *          наименоваие сущности: или project, или users, или issue
     * */
    public static void reportAll(String value) throws SQLException {
        String sql;
        switch (value){
            case "project":
                sql = "SELECT id, name FROM project;";
                break;
            case "users":
                sql = "SELECT id, name FROM users;";
                break;
            case "issue":
                sql = "SELECT issue.id, issue.name, project.name, users.name " +
                        "FROM issue, users, project " +
                        "WHERE issue.idproject=project.id AND issue.iduser=users.id;";
                break;
            default:
                return;
        }
        ResultSet result = statement.executeQuery(sql);
        int columns = result.getMetaData().getColumnCount();
        while (result.next()){
            for (int i = 1; i <= columns; i++) {
                System.out.print(result.getString(i) + "\t");
            }
            System.out.println();
        }
    }

    /**
     * Метод извлекает из БД весь список задач заведенных в рамках выбранного проекта и закрепленных за выбранным пользователем
     *
     * @param   project
     *          объект выбранного проекта
     *
     * @param   user
     *          объект выбранного пользователя
     * */
    public static boolean reportSome(Project project, User user) throws SQLException {
        //получаем id пользователя
        int idUser = getIDUser(user);
        //получаем id проекта
        int idProject = getIDProject(project);
        //если выбранный проект и пользователь не существуют в БД - завершаем работу
        if(idUser == -1 || idProject == -1) return false;
        //составляем текст sql-запроса
        String sql = String.format("SELECT issue.id, issue.name, project.name, users.name " +
                "FROM issue, users, project WHERE issue.idproject=project.id " +
                "AND issue.iduser=users.id AND issue.idproject=%d AND issue.iduser=%d;",idProject,idUser);
        //оправляем запрос на выполнение и получаем результирующий набор строк
        ResultSet result = statement.executeQuery(sql);
        //определяем кол-во столбцов в результирующем наборе
        int columns = result.getMetaData().getColumnCount();
        //в цикле извлекаем все элементы из результирущего набора
        while (result.next()){
            for (int i = 1; i <= columns; i++) {
                System.out.print(result.getString(i) + "\t");
            }
            System.out.println();
        }
        return true;
    }
}
