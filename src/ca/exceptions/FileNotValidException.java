package ca.exceptions;

/**
 * This class is an exception that indicates the input
 * file is not valid.
 *
 * @author Cady Zhou
 * @version 1.1
 * @since 1.1
 */
public class FileNotValidException extends Exception {

    /**
     * Creates this exception with error message and cause
     * @param message   a String of error message
     * @param cause     a {@link Throwable} that is the cause
     * @see Exception#Exception(String, Throwable)
     */
    public FileNotValidException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates this exception with error message
     * @param message   a String of error message
     * @see Exception#Exception(String)
     */
    public FileNotValidException(String message) {
        super(message);
    }
}
