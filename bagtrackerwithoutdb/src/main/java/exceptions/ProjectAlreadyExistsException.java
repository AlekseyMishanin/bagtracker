package exceptions;

public class ProjectAlreadyExistsException extends Exception {
    public ProjectAlreadyExistsException(String project) {
        super("Project already exists: " + project);
    }
}
