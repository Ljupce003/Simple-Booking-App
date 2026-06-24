package finki.emt.lab_emt.model.exceptions;

public class SmestuvanjeNotFoundException extends RuntimeException {
    public SmestuvanjeNotFoundException(Long id) {
        super("Smestuvanje with id: "+id + "is not found");
    }
}
