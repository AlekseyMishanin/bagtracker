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

    public void addProject(Project project) throws ProjectAlreadyExistsException, IOException;
    public void addIssue(Issue issue) throws IssueAlreadyExistsException, IOException;
    public void addUser(User user) throws IOException, UserAlreadyExistsException;
    public void reportAll(String value) throws IOException;
    public void reportSome(Project project, User user) throws IOException;
}
