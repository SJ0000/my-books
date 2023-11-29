package sungjin.mybooks.controller;


import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import sungjin.mybooks.exception.MyBooksException;
import sungjin.mybooks.exception.NotFound;

@Slf4j
@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(MyBooksException.class)
    public String error(MyBooksException e, Model model, HttpServletResponse response) {
        log.error("MyBooksException", e);

        HttpStatus status = HttpStatus.valueOf(e.getStatusCode());
        model.addAttribute("status",status.value());
        model.addAttribute("error",status.getReasonPhrase());

        response.setStatus(e.getStatusCode());
        return "error";
    }
}
