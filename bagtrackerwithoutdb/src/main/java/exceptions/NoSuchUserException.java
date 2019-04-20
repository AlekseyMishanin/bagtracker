package exceptions;

import java.io.IOException;

public class NoSuchUserException extends IOException {
    public NoSuchUserException(String name) {
        super("Not found user \"" + name + "\"");
    }
}
