package exceptions;

public class IssueAlreadyExistsException extends Exception {
    public IssueAlreadyExistsException(String issue) {
        super("Issue already exists: " + issue);
    }
}
