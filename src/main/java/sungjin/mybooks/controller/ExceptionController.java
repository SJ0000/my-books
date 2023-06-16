package sungjin.mybooks.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import sungjin.mybooks.exception.MyBooksException;
import sungjin.mybooks.response.ErrorResponse;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(MyBooksException.class)
    public ResponseEntity<ErrorResponse> error(MyBooksException e) {
        int statusCode = e.getStatusCode();

        ErrorResponse response = ErrorResponse.builder()
                .code(String.valueOf(statusCode))
                .message(e.getMessage())
                .build();

        return ResponseEntity.status(statusCode)
                .body(response);
    }

}
