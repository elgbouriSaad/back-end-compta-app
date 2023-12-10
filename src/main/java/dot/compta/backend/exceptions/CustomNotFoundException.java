package dot.compta.backend.exceptions;

import org.springframework.http.HttpStatus;

public class CustomNotFoundException extends CustomException {

    public CustomNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND.value(), message);
    }

}
