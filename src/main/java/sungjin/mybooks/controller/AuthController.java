package sungjin.mybooks.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.server.Cookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sungjin.mybooks.annotation.AuthRequired;
import sungjin.mybooks.config.data.UserSession;
import sungjin.mybooks.domain.Session;
import sungjin.mybooks.domain.User;
import sungjin.mybooks.dto.request.SignUp;
import sungjin.mybooks.dto.request.Login;
import sungjin.mybooks.service.AuthService;
import sungjin.mybooks.service.UserService;
import sungjin.mybooks.util.CookieNames;

import java.net.URI;
import java.time.Duration;


@Slf4j
@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthService authService;

    @GetMapping("/login")
    public String loginForm(){
        return "login";
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@Valid Login login){

        authService.validateUser(login.getEmail(),login.getPassword());
        User user = userService.findUser(login.getEmail());
        Session session = authService.createSession(user);

        ResponseCookie cookie = ResponseCookie.from(CookieNames.SESSION_ID, session.getAccessToken())
                .domain("localhost") // todo 서버환경에 따른 분리 설정화 필요
                .path("/")
                .httpOnly(true)
                .secure(false)
                .maxAge(Duration.ofDays(30))
                .sameSite(Cookie.SameSite.STRICT.toString())
                .build();

        return ResponseEntity.status(HttpStatus.FOUND)
                .header(HttpHeaders.SET_COOKIE,cookie.toString())
                .header(HttpHeaders.LOCATION,"/")
                .build();
    }

    @GetMapping("/signup")
    public String signUpForm(){
        System.out.println("signup called");
        return "signup";
    }

    @PostMapping("/signup")
    public String signUp(@Valid SignUp singUp){
        Long userId = userService.signUpUser(singUp);
        return "redirect:/login?after_signup=true";
    }

    @AuthRequired
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(UserSession userSession){

        authService.removeSession(userSession.getAccessToken());

        ResponseCookie cookie = ResponseCookie.from(CookieNames.SESSION_ID, "")
                .domain("localhost")
                .path("/")
                .httpOnly(true)
                .secure(false)
                .maxAge(0)
                .sameSite("Strict")
                .build();

        return ResponseEntity.noContent()
                .header(HttpHeaders.SET_COOKIE,cookie.toString())
                .build();
    }

}
