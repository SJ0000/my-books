package sungjin.mybooks.global.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sungjin.mybooks.domain.user.encrypt.PasswordEncoderProperties;
import sungjin.mybooks.domain.user.encrypt.CustomPasswordEncoder;
import sungjin.mybooks.domain.user.encrypt.PasswordEncoder;

@Configuration
@EnableConfigurationProperties(PasswordEncoderProperties.class)
public class AuthConfig {

    private final PasswordEncoderProperties properties;

    public AuthConfig(PasswordEncoderProperties properties) {
        this.properties = properties;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new CustomPasswordEncoder(properties);
    }
}
