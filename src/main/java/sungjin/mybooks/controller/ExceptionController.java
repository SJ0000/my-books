package sungjin.mybooks.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import sungjin.mybooks.exception.MyBooksException;
import sungjin.mybooks.dto.response.ErrorResponse;
import sungjin.mybooks.exception.NotFound;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(MyBooksException.class)
    public String error(MyBooksException e, Model model) {
        model.addAttribute("status", e.getStatusCode());
        if(e instanceof NotFound){
            model.addAttribute("message", "페이지를 찾을 수 없습니다.");
        }else{
            model.addAttribute("message", e.getMessage());
        }

        return "error";
    }
}
