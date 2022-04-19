package exceptions;

/**
 * Class that extend Exception to create an exception for the case of a too low thrust at lift off.
 *
 * @author Benjamin P
 * @version 22.03.06 (1.0.0)
 * @since 06/03/22
 */
public class TooLowThrustException extends Exception {

    /**
     * Constructor of the exception.
     */
    public TooLowThrustException() {
        super("The thrust produced by the rocket is not enough for the rocket you produced");
    }
}
