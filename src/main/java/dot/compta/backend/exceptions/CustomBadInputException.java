package dot.compta.backend.exceptions;

import org.springframework.http.HttpStatus;


public class CustomBadInputException extends CustomException {

    public CustomBadInputException(String message) {
        super(HttpStatus.BAD_REQUEST.value(), message);
    }
}
