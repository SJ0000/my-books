package sungjin.mybooks.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import sungjin.mybooks.domain.User;
import sungjin.mybooks.dto.response.UserResponse;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(){
        return "home";
    }
}
