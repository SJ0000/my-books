package sungjin.mybooks.domain.common.aop;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import sungjin.mybooks.domain.common.annotation.DomainAuthorize;
import sungjin.mybooks.domain.common.annotation.DomainId;
import sungjin.mybooks.domain.common.annotation.UserId;
import sungjin.mybooks.domain.common.domain.security.DomainAuthorizationManager;

@Aspect
@RequiredArgsConstructor
public class DomainAuthorizeAspect {

    private static final String USER_ID_VARIABLE_NAME = "userId";

    private final DomainAuthorizationManager authorizationManager;

    @Before("@annotation(domainAuthorize)")
    public void authorizeDomain(JoinPoint joinPoint, DomainAuthorize domainAuthorize) {
        Class<?> domainType = domainAuthorize.value();
        Long userId = getUserId(joinPoint);
        Long domainId = getDomainId(joinPoint, domainType.getSimpleName());

        authorizationManager.authorize(userId, domainType, domainId);
    }

    private Long getUserId(JoinPoint joinPoint) {
        return ParameterExtractor.extractByAnnotation(joinPoint, UserId.class)
                .orElseGet(() -> ParameterExtractor.extractByName(joinPoint, USER_ID_VARIABLE_NAME)
                        .orElseThrow(() -> new RuntimeException("parameter not found")));
    }

    private Long getDomainId(JoinPoint joinPoint, String domainName) {
        String parameterName = domainName+"id";
        return ParameterExtractor.extractByAnnotation(joinPoint, DomainId.class)
                .orElseGet(() -> ParameterExtractor.extractByName(joinPoint, parameterName)
                        .orElseThrow(() -> new RuntimeException("parameter not found")));
    }
}
