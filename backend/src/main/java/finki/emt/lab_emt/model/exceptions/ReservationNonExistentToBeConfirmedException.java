package finki.emt.lab_emt.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class ReservationNonExistentToBeConfirmedException extends Exception{

    public ReservationNonExistentToBeConfirmedException(Long id) {
        super(String.format("Category with id: %d is not found",id));
    }
}
