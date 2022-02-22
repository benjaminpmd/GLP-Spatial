package exceptions;

public class MissingStageException extends Exception {
    
    public MissingStageException() {
        super("One ore more stage are missing, please init them before adding payload");
    }
}
