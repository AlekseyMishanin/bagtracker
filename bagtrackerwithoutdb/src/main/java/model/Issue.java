package model;

import exceptions.NoSuchProjectException;
import exceptions.NoSuchUserException;
import lombok.NonNull;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Formatter;

/**
 * Класс инкапсулирует задачу.
 *
 * @author Mishanin Aleksey
 * */
public class Issue extends Essence{

    private Project project;                    //проект в рамках которого заводится задача
    private User user;                          //пользователь ответственный за выполение данной задачи

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
    public Issue(@NonNull String name, @NonNull final Project project, @NonNull final User user) {
        super(name);
        this.project = project;
        this.user = user;
    }

    /**
     * Переопределенный метод родительского класса. Метод позволяет внести в базу данных новую задачу в рамках
     * существующего проекта, закрепленую за выбранным пользователем.
     *
     * @throws  NoSuchProjectException
     *          выбранный проект не существует в базе данных
     *
     * @throws  NoSuchUserException
     *          выбранный пользователь не существует в базе данных
     * */
    @Override
    public void writeToFile() throws IOException {
        //проверяем существует ли проект/пользователь в БД
        if(project.exists() && user.exists()){
            //открываем символьный поток для записи задачи в файл
            PrintWriter fr = new PrintWriter(new FileWriter("bagtracker/issues.txt",true), true);
            Formatter formatter = new Formatter();
            //настраиваем формат ввода данных
            formatter.format("%s %s %s",getName(), this.project.getName(), this.user.getName());
            //записываем задачу
            fr.println(formatter);
            fr.close();
        } else if(!project.exists()){
            throw new NoSuchProjectException(project.getName());
        } else if(!user.exists()){
            throw new NoSuchUserException(user.getName());
        }
    }

    /**
     * Переопределенный метод родительского класса. Позволяет проверить существование объекта в базе данных
     * */
    @Override
    public boolean exists() throws IOException {
        //используя метод lines() NIO получаем Stream. Далее фильтрами проверяем существование объекта
        return Files.lines(Paths.get("bagtracker","users.txt"))
                .filter((n)->n.indexOf(getName())!=-1)          //проверяем имя сущности
                .filter((n)->n.indexOf(project.getName())!=-1)  //проверяем имя проекта
                .filter((n)->n.indexOf(user.getName())!=-1)     //проверяем имя пользователя
                .count()>0;                                     //на выходе получаем или 0 если нет совпадений, или >0 в противном случае
    }

    public Project getProject() {
        return project;
    }

    public User getUser() {
        return user;
    }
}
