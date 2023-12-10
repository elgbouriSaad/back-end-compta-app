package dot.compta.backend.exceptions;

import org.springframework.http.HttpStatus;

public class CustomDeletedException extends CustomException {

    public CustomDeletedException(String message) {
        super(HttpStatus.NOT_FOUND.value(), message);
    }

}
