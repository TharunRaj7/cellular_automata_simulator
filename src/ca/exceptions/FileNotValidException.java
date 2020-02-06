package ca.exceptions;

public class FileNotValidException extends Exception {
    public FileNotValidException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileNotValidException(String message) {
        super(message);
    }
}
