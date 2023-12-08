package sungjin.mybooks.global.resolver;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import sungjin.mybooks.global.data.UserSession;
import sungjin.mybooks.domain.user.domain.Session;
import sungjin.mybooks.global.exception.NotFound;
import sungjin.mybooks.global.exception.Unauthorized;
import sungjin.mybooks.domain.user.service.AuthService;
import sungjin.mybooks.global.util.CookieNames;
import sungjin.mybooks.global.util.CookieUtils;

import java.util.Optional;

/*
  Cookie로 전달된 Session id를 읽어서 Controller에 UserSession 객체로 넘겨주는 역할
 */
@RequiredArgsConstructor
public class UserSessionResolver implements HandlerMethodArgumentResolver {

    private final AuthService authService;


    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(UserSession.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = Optional.ofNullable(webRequest.getNativeRequest(HttpServletRequest.class))
                .orElseThrow(Unauthorized::new);

        String sessionId  = CookieUtils.getCookieValue(request.getCookies(), CookieNames.SESSION_ID)
                        .orElseThrow(()-> new NotFound(Cookie.class,"name",CookieNames.SESSION_ID));

        Session session = authService.findSession(sessionId)
                .orElseThrow(Unauthorized::new);

        return UserSession.builder()
                .sessionId(sessionId)
                .userId(session.getUserId())
                .build();
    }
}
