package exceptions;

public class TooLowThrustException extends Exception {
    
    public TooLowThrustException() {
        super("The thrust produced by the rocket is not enough for the rocket you produced");
    }
}
