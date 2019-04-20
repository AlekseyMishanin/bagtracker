package utility;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Класс создает структуру каталогов/файлов в случае отсутствия необходимых каталогов/файлов в файловой системе
 *
 * @author Mishanin Aleksey
 * */
public class Initial {

    private static final Logger log = Logger.getLogger(Initial.class);  //логгер для вывода ошибок
    private static Initial init = new Initial();                        //загружаем и инициализуруем класс
    public static boolean isOk = true;                                  //переменная для проверки структуры каталогов/файлов

    /**
     * Конструктор по умолчанию. Проверяет существование каталогов/файлов в файловой системе. При отсутствии каталогов/файлов
     * при помощи библиотеки NIO создает необходимые элементы структуры. В случае появления исключительных ситуаций
     * во время создания каталогов/файлов в логгер вносится сообщение об ошибке, а логической переменной отвечающей
     * за корректное состояние структуры каталогов присваивается ложное значение.
     * */
    private Initial(){
        if(!Files.exists(Paths.get("bagtracker"))) {
            try {
                Files.createDirectory(Paths.get("bagtracker"));
            } catch (IOException e) {
                log.error(e.getMessage());
                isOk = false;
            }
        }

        if (!Files.exists(Paths.get("bagtracker","users.txt"))) {
            try {
                Files.createFile(Paths.get("bagtracker","users.txt"));
            } catch (IOException e) {
                log.error(e.getMessage());
                isOk = false;
            }
        }

        if (!Files.exists(Paths.get("bagtracker","project.txt"))) {
            try {
                Files.createFile(Paths.get("bagtracker","project.txt"));
            } catch (IOException e) {
                log.error(e.getMessage());
                isOk = false;
            }
        }

        if (!Files.exists(Paths.get("bagtracker","issues.txt"))) {
            try {
                Files.createFile(Paths.get("bagtracker","issues.txt"));
            } catch (IOException e) {
                log.error(e.getMessage());
                isOk = false;
            }
        }
    }
}
