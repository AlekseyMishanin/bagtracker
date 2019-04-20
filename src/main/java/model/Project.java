package model;

import lombok.NonNull;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

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
    public void writeToFile() throws IOException {
        PrintWriter fr = new PrintWriter(new FileWriter("bagtracker/project.txt",true), true);
        fr.println(getName());
        fr.close();
    }

    /**
     * Переопределенный метод родительского класса. Позволяет проверить существование объекта в базе данных
     * */
    @Override
    public boolean exists() throws IOException {
        //используя метод lines() NIO получаем Stream. Далее фильтрами проверяем существование объекта
        return Files.lines(Paths.get("bagtracker","project.txt"))
                .filter((n)->n.equals(getName()))           //проверяем имя проекта
                .count()>0;                                 //на выходе получаем или 0 если нет совпадений, или >0 в противном случае
    }
}
