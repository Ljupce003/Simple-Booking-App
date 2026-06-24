package finki.emt.lab_emt.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class CategoryNotFoundException extends Exception{

    public CategoryNotFoundException(Long id) {
        super(String.format("Category with id: %d is not found",id));
    }
}





