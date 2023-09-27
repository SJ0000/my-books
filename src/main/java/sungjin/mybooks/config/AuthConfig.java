package sungjin.mybooks.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sungjin.mybooks.config.data.PasswordEncoderProperties;

@Configuration
@EnableConfigurationProperties(PasswordEncoderProperties.class)
public class AuthConfig {

    private final PasswordEncoderProperties properties;

    public AuthConfig(PasswordEncoderProperties properties) {
        this.properties = properties;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new PasswordEncoder(properties);
    }
}
