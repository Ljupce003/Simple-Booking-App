package finki.emt.lab_emt.model.exceptions;

public class InvalidArgumentsException extends RuntimeException{

    public InvalidArgumentsException() {
        super("Invalid arguments were provided");
    }
}
