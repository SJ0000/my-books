package sungjin.mybooks.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import sungjin.mybooks.util.CookieUtils;


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
        authService.login(login);

        User user = userService.findUserByEmail(login.getEmail());
        Session session = authService.createSession(user);

        ResponseCookie cookie = CookieUtils.createCookie(CookieNames.SESSION_ID, session.getAccessToken());

        return ResponseEntity.status(HttpStatus.FOUND)
                .header(HttpHeaders.SET_COOKIE,cookie.toString())
                .header(HttpHeaders.LOCATION,"/")
                .build();
    }

    @GetMapping("/signup")
    public String signUpForm(){
        return "signup";
    }

    @PostMapping("/signup")
    public String signUp(@Valid SignUp singUp){
        userService.signUpUser(singUp);
        return "redirect:/login?after_signup=true";
    }

    @AuthRequired
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(UserSession userSession){

        authService.removeSession(userSession.getAccessToken());

        ResponseCookie cookieForExpire = CookieUtils.createCookieForExpire(CookieNames.SESSION_ID);

        return ResponseEntity.noContent()
                .header(HttpHeaders.SET_COOKIE,cookieForExpire.toString())
                .build();
    }

}
