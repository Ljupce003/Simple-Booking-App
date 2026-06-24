package finki.emt.lab_emt.model.exceptions;

public class ManufacturerNotFoundException extends Exception{

    public ManufacturerNotFoundException(Long id) {
        super(String.format("Manufacturer with id: %d was not found", id));
    }

}
