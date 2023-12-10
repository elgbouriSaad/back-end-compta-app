package dot.compta.backend.exceptions;

import org.springframework.http.HttpStatus;

public class CustomInvalidStatusException extends CustomException{
	
	public CustomInvalidStatusException(String message) {
		super(HttpStatus.BAD_REQUEST.value(), message);
	}

}
