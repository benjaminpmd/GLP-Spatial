package exceptions;

/**
 * Class that extend Exception to create an exception for missing part of the rocket.
 *
 * @author Benjamin P
 * @version 22.03.06 (1.0.0)
 * @since 06/03/22
 */
public class MissingPartException extends Exception {

    /**
     * Constructor of the exception.
     */
    public MissingPartException() {
        super("A stage or the payload is missing from the rocket.");
    }
}
