import model.Issue;
import model.Project;
import model.User;

import java.sql.SQLException;

/**
 * Интерфейс объявляет основные методы доступные в рамках консольного дебаг-трекера.
 *
 * @author Mishanin Aleksey
 * */
public interface Command {

    void addProject(Project project);
    void addIssue(Issue issue);
    void addUser(User user);
    void reportAll(String value) throws SQLException;
    void reportSome(Project project, User user) throws SQLException;
}
