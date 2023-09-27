package sungjin.mybooks.config;


import jakarta.servlet.RequestDispatcher;
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

import java.util.Collections;
import java.util.Optional;

/*
    요청에 Session Id가 Cookie에 들어있다면, 사용자 정보를 찾아 UserInfo 객체에 담아 Model에 넘겨주는 Interceptor
 */
@RequiredArgsConstructor
public class UserInfoInterceptor implements HandlerInterceptor {

    private final SessionRepository sessionRepository;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("UserInfoInterceptor.postHandle");
        Optional<String> optionalSessionId = CookieUtils.getCookieValue(request.getCookies(), CookieNames.SESSION_ID);

        if(optionalSessionId.isEmpty() || modelAndView == null){
            return;
        }

        Session session = sessionRepository.findByAccessToken(optionalSessionId.get())
                .orElseThrow(() -> new RuntimeException("사용자 정보를 가져오는데 실패했습니다."));

        modelAndView.addObject("user",new UserResponse(session.getUser()));
    }
}
