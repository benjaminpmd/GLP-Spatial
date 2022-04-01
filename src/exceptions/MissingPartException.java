package exceptions;

/**
 * Exception the
 */
public class MissingPartException extends Exception {
    
    public MissingPartException() {
        super("A stage or the payload is missing from the rocket.");
    }
}
