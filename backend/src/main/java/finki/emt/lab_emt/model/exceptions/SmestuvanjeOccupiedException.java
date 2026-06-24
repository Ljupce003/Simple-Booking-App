package finki.emt.lab_emt.model.exceptions;

public class SmestuvanjeOccupiedException extends RuntimeException {

    public SmestuvanjeOccupiedException() {
        super("Smestuvanje is already occupied");
    }

}


