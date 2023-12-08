package sungjin.mybooks.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import sungjin.mybooks.global.interceptor.AuthInterceptor;
import sungjin.mybooks.global.interceptor.ModelInterceptor;
import sungjin.mybooks.global.resolver.UserSessionResolver;
import sungjin.mybooks.domain.user.service.AuthService;
import sungjin.mybooks.domain.user.service.UserService;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private final AuthService authService;
    private final UserService userService;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthInterceptor(authService));
        registry.addInterceptor(new ModelInterceptor(authService, userService));
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new UserSessionResolver(authService));
    }



}
