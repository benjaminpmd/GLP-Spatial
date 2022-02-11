package exceptions;

public class PayloadWeightTooHighException extends Exception {
    
    public PayloadWeightTooHighException() {
        super("The weight of the payload is too high for the rocket you created");
    }
}
