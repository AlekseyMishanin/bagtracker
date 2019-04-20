package exceptions;

public class UserAlreadyExistsException extends Exception {
    public UserAlreadyExistsException(String user) {
        super("User already exists: " + user);
    }
}
