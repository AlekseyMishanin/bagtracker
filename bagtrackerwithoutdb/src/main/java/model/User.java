package model;

import lombok.NonNull;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

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
    public void writeToFile() throws IOException {
        PrintWriter fr = new PrintWriter(new FileWriter("bagtracker/users.txt",true), true);
        fr.println(getName());
        fr.close();
    }

    /**
     * Переопределенный метод родительского класса. Позволяет проверить существование объекта в базе данных
     * */
    @Override
    public boolean exists() throws IOException {
        //используя метод lines() NIO получаем Stream. Далее фильтрами проверяем существование объекта
        return Files.lines(Paths.get("bagtracker","users.txt"))
                .filter((n)->n.equals(getName()))               //проверяем имя пользователя
                .count()>0;                                     //на выходе получаем или 0 если нет совпадений, или >0 в противном случае
    }
}
