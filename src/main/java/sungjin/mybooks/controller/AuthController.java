package sungjin.mybooks.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @PostMapping("/login")
    public String login(){



        return "ok";
    }

    @PostMapping("/logout")
    public String logout(){

        return "ok";
    }
}
