package sungjin.mybooks.config;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import sungjin.mybooks.domain.Session;
import sungjin.mybooks.domain.User;
import sungjin.mybooks.dto.response.UserResponse;
import sungjin.mybooks.repository.SessionRepository;
import sungjin.mybooks.util.CookieNames;
import sungjin.mybooks.util.CookieUtils;
import sungjin.mybooks.util.ThymeleafUtils;

import java.util.Optional;

/*
    요청에 Session Id가 Cookie에 들어있다면, 사용자 정보를 찾아 UserInfo 객체에 담아 Model에 넘겨주는 Interceptor
 */
@RequiredArgsConstructor
public class ModelInterceptor implements HandlerInterceptor {

    private final SessionRepository sessionRepository;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if(modelAndView == null)
            return;

        modelAndView.addObject("requestUri",request.getRequestURI());
        modelAndView.addObject("util",new ThymeleafUtils());

        if(CookieUtils.hasCookie(request.getCookies(),CookieNames.SESSION_ID)) {
            String accessToken = CookieUtils.getCookieValue(request.getCookies(), CookieNames.SESSION_ID).get();
            addUserInfoIfExists(modelAndView,accessToken);
        }
    }

    private void addUserInfoIfExists(ModelAndView modelAndView, String accessToken){
        Optional<Session> optionalSession = sessionRepository.findByAccessToken(accessToken);

        if (optionalSession.isEmpty())
            return;

        User user = optionalSession.get().getUser();
        modelAndView.addObject("user", new UserResponse(user));
    }

}
