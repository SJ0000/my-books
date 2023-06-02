package sungjin.mybooks.controller;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sungjin.mybooks.config.data.UserSession;
import sungjin.mybooks.domain.Session;
import sungjin.mybooks.domain.User;
import sungjin.mybooks.request.Login;
import sungjin.mybooks.service.AuthService;
import sungjin.mybooks.service.UserService;

@Slf4j
@RestController("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthService authService;


    @PostMapping("/login")
    public String login(@Valid @RequestBody Login login, HttpServletResponse response){
        log.info("email = {}, password = {}",login.getEmail(),login.getPassword());

        // db에서 조회
        authService.validateUser(login.getEmail(),login.getPassword());
        User user = userService.findUser(login.getEmail());

        // Session 생성
        Session session = authService.createSession(user);

        // 토큰 응답
        response.addCookie(new Cookie("mybooks_sessionid",session.getAccessToken()));
        return session.getAccessToken();
    }

    @PostMapping("/logout")
    public String logout(){

        return "ok";
    }
}
