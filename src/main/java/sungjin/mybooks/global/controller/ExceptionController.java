package sungjin.mybooks.global.controller;


import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import sungjin.mybooks.global.exception.MyBooksException;

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
