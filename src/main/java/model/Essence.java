package model;

import lombok.NonNull;

import java.io.IOException;

/**
 * Абстрактный класс инкапсулирует сущность.
 *
 * @author Mishanin Aleksey
 * */
public abstract class Essence {

    private String name;                                    //наименование сущности

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

    abstract public void writeToFile() throws IOException;
    abstract boolean exists() throws IOException;
}
