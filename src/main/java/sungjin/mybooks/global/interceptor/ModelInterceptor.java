package sungjin.mybooks.global.interceptor;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import sungjin.mybooks.domain.user.domain.Session;
import sungjin.mybooks.domain.user.domain.User;
import sungjin.mybooks.domain.user.model.UserModel;
import sungjin.mybooks.domain.user.service.AuthService;
import sungjin.mybooks.domain.user.service.UserService;
import sungjin.mybooks.global.util.CookieNames;
import sungjin.mybooks.global.util.CookieUtils;
import sungjin.mybooks.global.util.ThymeleafUtils;

import java.util.Optional;

/*
    요청에 Session Id가 Cookie에 들어있다면, 사용자 정보를 찾아 UserInfo 객체에 담아 Model에 넘겨주는 Interceptor
 */
@RequiredArgsConstructor
public class ModelInterceptor implements HandlerInterceptor {

    private static final String THYMELEAF_UTILITY = "util";
    private static final String USER_INFO = "user";

    private final AuthService authService;
    private final UserService userService;

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
            User user = userService.findUserById(session.getUserId());
            modelAndView.addObject(USER_INFO, new UserModel(user));
        });
    }
}
