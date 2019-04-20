package model;

import lombok.NonNull;

import java.sql.SQLException;

/**
 * Абстрактный класс инкапсулирует сущность.
 *
 * @author Mishanin Aleksey
 * */
public abstract class Essence {

    //наименование сущности
    private String name;

    /**
     * Конструктор с параметрами
     *
     * @param   name
     *          наименование сущности
     * */
    public Essence(@NonNull String name) {
        this.name = name;
    }

    /**
     * Геттер
     * */
    public String getName() {
        return name;
    }

    abstract public boolean writeToDB();
    abstract public int getID() throws SQLException;
}
