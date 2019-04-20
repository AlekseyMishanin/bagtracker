package exceptions;

import java.io.IOException;

public class NoSuchProjectException extends IOException {
    public NoSuchProjectException(String name) {
        super("Not found project \"" + name + "\"");
    }
}
