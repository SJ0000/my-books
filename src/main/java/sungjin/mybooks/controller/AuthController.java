package sungjin.mybooks.controller;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
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
import sungjin.mybooks.util.CookieNames;
import sungjin.mybooks.util.CookieUtils;

import java.time.Duration;
import java.util.UUID;

import static org.springframework.boot.web.server.Cookie.SameSite.*;

@Slf4j
@RestController("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthService authService;


    @PostMapping("/login")
    public ResponseEntity<Void> login(@Valid @RequestBody Login login){
        log.info("email = {}, password = {}",login.getEmail(),login.getPassword());

        authService.validateUser(login.getEmail(),login.getPassword());
        User user = userService.findUser(login.getEmail());
        Session session = authService.createSession(user);

        ResponseCookie cookie = ResponseCookie.from(CookieNames.SESSION_ID, session.getAccessToken())
                .domain("localhost") // todo 서버환경에 따른 분리 설정화 필요
                .path("/")
                .httpOnly(true)
                .secure(false)
                .maxAge(Duration.ofDays(30))
                .sameSite("Strict")
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE,cookie.toString())
                .build();
    }

    @PostMapping("/logout")
    public String logout(){

        return "ok";
    }
}
