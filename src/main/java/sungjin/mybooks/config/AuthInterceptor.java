package sungjin.mybooks.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import sungjin.mybooks.annotation.AuthRequired;
import sungjin.mybooks.domain.Session;
import sungjin.mybooks.exception.Unauthorized;
import sungjin.mybooks.service.AuthService;
import sungjin.mybooks.util.CookieNames;
import sungjin.mybooks.util.CookieUtils;

import java.util.Optional;

@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

    private final AuthService authService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(!(handler instanceof HandlerMethod handlerMethod))
            return true;

        if(!isAuthenticateRequired(handlerMethod))
            return true;

        if(!isAuthenticated(request))
            throw new Unauthorized();

        return true;
    }

    private boolean isAuthenticated(HttpServletRequest request){
        Optional<String> optionalAccessToken = CookieUtils.getCookieValue(request.getCookies(), CookieNames.SESSION_ID);
        if(optionalAccessToken.isEmpty())
            return false;

        return authService.isValidAccessToken(optionalAccessToken.get());
    }

    private boolean isAuthenticateRequired(HandlerMethod handlerMethod){
        return handlerMethod.hasMethodAnnotation(AuthRequired.class);
    }

}
