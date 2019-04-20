package model;

import lombok.NonNull;
import utility.DBServise;

import java.sql.SQLException;

/**
 * Класс инкапсулизует пользователя
 *
 * @author Mishanin Aleksey
 * */
public class User extends Essence{

    /**
     * Конструктор с параметрами
     *
     * @param   name
     *          наименование сущности
     * */
    public User(@NonNull String name) {
        super(name);
    }

    /**
     * Переопределенный метод родительского класса. Метод позволяет внести в базу данных нового пользователя.
     * */
    @Override
    public boolean writeToDB()  {
        return DBServise.insertUser(this);
    }

    @Override
    public int getID() throws SQLException {
        return DBServise.getIDUser(this);
    }
}
