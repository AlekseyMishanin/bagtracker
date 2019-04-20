import exceptions.IssueAlreadyExistsException;
import exceptions.ProjectAlreadyExistsException;
import exceptions.UserAlreadyExistsException;
import model.Issue;
import model.Project;
import model.User;

import java.io.IOException;

/**
 * Интерфейс объявляет основные методы доступные в рамках консольного дебаг-трекера.
 *
 * @author Mishanin Aleksey
 * */
public interface Command {

    void addProject(Project project) throws ProjectAlreadyExistsException, IOException;
    void addIssue(Issue issue) throws IssueAlreadyExistsException, IOException;
    void addUser(User user) throws IOException, UserAlreadyExistsException;
    void reportAll(String value) throws IOException;
    void reportSome(Project project, User user) throws IOException;
}
