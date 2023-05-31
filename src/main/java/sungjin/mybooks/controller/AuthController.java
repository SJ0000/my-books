package sungjin.mybooks.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sungjin.mybooks.config.data.UserSession;
import sungjin.mybooks.request.Login;
import sungjin.mybooks.service.AuthService;

@Slf4j
@RestController("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping("/test")
    public String authTest(UserSession session){



        return "ok";
    }

    @PostMapping("/login")
    public String login(@Valid @RequestBody Login login){
        log.info("email = {}, password = {}",login.getEmail(),login.getPassword());

        // db에서 조회
        authService.validateUser(login.getEmail(),login.getPassword());


        // 토큰 응답


        return "ok";
    }

    @PostMapping("/logout")
    public String logout(){

        return "ok";
    }
}
