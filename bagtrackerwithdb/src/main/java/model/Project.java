package model;

import lombok.NonNull;
import utility.DBServise;

import java.sql.SQLException;

/**
 * Класс инкапсулизует проект
 *
 * @author Mishanin Aleksey
 * */
public class Project extends Essence {

    /**
     * Конструктор с параметрами
     *
     * @param   name
     *          наименование сущности
     * */
    public Project(@NonNull String name) {
        super(name);
    }

    /**
     * Переопределенный метод родительского класса. Метод позволяет внести в базу данных новый проект.
     * */
    @Override
    public boolean writeToDB() {
        return DBServise.insertProject(this);
    }

    @Override
    public int getID() throws SQLException {
        return DBServise.getIDProject(this);
    }
}
