package sungjin.mybooks.config;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import sungjin.mybooks.domain.Session;
import sungjin.mybooks.domain.User;
import sungjin.mybooks.dto.response.UserResponse;
import sungjin.mybooks.repository.SessionRepository;
import sungjin.mybooks.service.AuthService;
import sungjin.mybooks.util.CookieNames;
import sungjin.mybooks.util.CookieUtils;
import sungjin.mybooks.util.ThymeleafUtils;

import java.util.Collections;
import java.util.Optional;

/*
    요청에 Session Id가 Cookie에 들어있다면, 사용자 정보를 찾아 UserInfo 객체에 담아 Model에 넘겨주는 Interceptor
 */
@RequiredArgsConstructor
public class ModelInterceptor implements HandlerInterceptor {

    private static final String THYMELEAF_UTILITY = "util";
    private static final String USER_INFO = "user";

    private final AuthService authService;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (modelAndView == null)
            return;

        modelAndView.addObject(THYMELEAF_UTILITY, new ThymeleafUtils(request));

        CookieUtils.getCookieValue(request.getCookies(), CookieNames.SESSION_ID)
                .ifPresent((accessToken) -> {
                    addUserInfoIfExists(modelAndView, accessToken);
                });
    }

    private void addUserInfoIfExists(ModelAndView modelAndView, String accessToken) {
        Optional<Session> optionalSession = authService.findSession(accessToken);
        optionalSession.ifPresent((session) -> {
            User user = session.getUser();
            modelAndView.addObject(USER_INFO, new UserResponse(user));
        });
    }
}
