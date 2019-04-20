package model;


import lombok.NonNull;
import utility.DBServise;
import java.sql.SQLException;

/**
 * Класс инкапсулирует задачу.
 *
 * @author Mishanin Aleksey
 * */
public class Issue extends Essence{

    private int idproject;                  //проект в рамках которого заводится задача
    private int iduser;                     //пользователь ответственный за выполение данной задачи

     /**
     * Конструктор с параметрами.
     *
     * @param   name
     *          наименование сущности
     *
     * @param   project
     *          объект проекта
     *
     * @param   user
     *          объект пользователя
     * */
    public Issue(@NonNull String name, Project project, User user) throws SQLException {
        super(name);
        this.idproject = DBServise.getIDProject(project);
        this.iduser = DBServise.getIDUser(user);
    }

    /**
     * Переопределенный метод родительского класса. Метод позволяет внести в базу данных новую задачу в рамках
     * существующего проекта, закрепленую за выбранным пользователем.
     * */
    @Override
    public boolean writeToDB() {
        return DBServise.insertIssue(this);
    }

    @Override
    public int getID() throws SQLException {
        return DBServise.getIDIssuet(this);
    }

    public int getIdproject() {
        return idproject;
    }

    public int getIduser() {
        return iduser;
    }
}
