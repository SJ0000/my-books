package sungjin.mybooks.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import sungjin.mybooks.exception.MyBooksException;
import sungjin.mybooks.dto.response.ErrorResponse;
import sungjin.mybooks.exception.NotFound;

@Slf4j
@ControllerAdvice
public class ExceptionController {

    private static final String ERROR_MESSAGE = "잘못된 요청입니다.";

    @ExceptionHandler(MyBooksException.class)
    public String error(MyBooksException e, Model model) {
        log.error("MyBooksException", e);
        ErrorResponse error = new ErrorResponse(String.valueOf(e.getStatusCode()), ERROR_MESSAGE);
        model.addAttribute("error", error);
        return "error";
    }

    @ExceptionHandler(Exception.class)
    public String unknownError(Exception e, Model model) {
        log.error("unknown error", e);
        ErrorResponse error = new ErrorResponse("500", "Unknown Error");
        model.addAttribute("error", error);
        return "error";
    }
}
