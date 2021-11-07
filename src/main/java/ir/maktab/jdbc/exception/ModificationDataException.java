package ir.maktab.jdbc.exception;

public class ModificationDataException extends RuntimeException{
    public ModificationDataException() {
    }

    public ModificationDataException(String message) {
        super(message);
    }

    public ModificationDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
