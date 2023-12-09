package sungjin.mybooks.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sungjin.mybooks.domain.common.aop.DomainAuthorizeAspect;

@Configuration
@RequiredArgsConstructor
public class AopConfig {

    private final


    @Bean
    DomainAuthorizeAspect domainAuthorizeAspect(){
        DomainAuthorizeManager
    }
}
