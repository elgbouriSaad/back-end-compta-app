package dot.compta.backend.controllers;

import dot.compta.backend.exceptions.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;

@RestControllerAdvice
@Slf4j
public class ExceptionHandlerController {

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ProblemDetail handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setType(URI.create("payload:invalid"));
        problemDetail.setDetail(
                exception.getFieldErrors()
                        .stream()
                        .map(e -> String.format("%s : %s", e.getField(), e.getDefaultMessage()))
                        .toList().toString()
        );
        return problemDetail;
    }

    @ExceptionHandler(value = {CustomException.class})
    public ProblemDetail handleMethodArgumentNotValidException(CustomException exception) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(exception.getStatusCode());
        problemDetail.setDetail(exception.getMessage());
        log.error(String.format("An error has occurred : %s", exception.getLocalizedMessage()));
        return problemDetail;
    }

    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ProblemDetail handleGenericException(Exception exception) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        problemDetail.setType(URI.create("application:error"));
        problemDetail.setDetail("An error has occurred please contact support");
        log.error(String.format("An error has occurred : %s", exception.getLocalizedMessage()));
        return problemDetail;
    }

}
