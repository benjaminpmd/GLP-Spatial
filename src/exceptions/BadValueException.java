package exceptions;

public class BadValueException extends Exception {
    public BadValueException(String name, String value) {
        super("Value of " + name + " cannot be " + value);
    }
}
