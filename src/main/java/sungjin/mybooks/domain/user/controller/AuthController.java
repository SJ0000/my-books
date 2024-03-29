package sungjin.mybooks.domain.user.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import sungjin.mybooks.domain.common.annotation.AuthenticationRequired;
import sungjin.mybooks.domain.user.domain.Session;
import sungjin.mybooks.domain.user.domain.User;
import sungjin.mybooks.domain.user.dto.Login;
import sungjin.mybooks.domain.user.dto.SignUp;
import sungjin.mybooks.domain.user.service.AuthService;
import sungjin.mybooks.domain.user.service.UserService;
import sungjin.mybooks.global.data.UserSession;
import sungjin.mybooks.global.util.CookieNames;
import sungjin.mybooks.global.util.CookieUtils;


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
        userService.validateForLogin(login);

        User user = userService.findUserByEmail(login.getEmail());
        Session session = authService.createSession(user.getId());

        ResponseCookie cookie = CookieUtils.createCookie(CookieNames.SESSION_ID, session.getId());

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

    @AuthenticationRequired
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(UserSession userSession){

        authService.removeSession(userSession.getSessionId());

        ResponseCookie cookieForExpire = CookieUtils.createCookieForExpire(CookieNames.SESSION_ID);

        return ResponseEntity.noContent()
                .header(HttpHeaders.SET_COOKIE,cookieForExpire.toString())
                .build();
    }

}
