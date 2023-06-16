package sungjin.mybooks.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import sungjin.mybooks.annotation.AuthRequired;
import sungjin.mybooks.exception.Unauthorized;
import sungjin.mybooks.util.CookieNames;
import sungjin.mybooks.util.CookieUtils;

public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        if(!isAuthenticated(request,handlerMethod))
            throw new Unauthorized();

        return true;
    }

    private boolean isAuthenticated(HttpServletRequest request ,HandlerMethod handler){
        return handler.hasMethodAnnotation(AuthRequired.class) && CookieUtils.hasCookie(request.getCookies(), CookieNames.SESSION_ID);
    }
}
