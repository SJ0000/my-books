package sungjin.mybooks.config.data;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties("auth.password-encoder")
public class PasswordEncoderProperties {
    private String salt;
    private int keyStretchCount;
}
