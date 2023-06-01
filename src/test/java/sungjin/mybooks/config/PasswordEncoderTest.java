package sungjin.mybooks.config;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.properties.PropertyMapping;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import sungjin.mybooks.config.data.PasswordEncoderProperties;

import static org.assertj.core.api.Assertions.*;


class PasswordEncoderTest {

    static PasswordEncoder passwordEncoder;

    @BeforeAll
    static void init(){
        System.out.println("init");
        PasswordEncoderProperties properties = new PasswordEncoderProperties();
        properties.setSalt("test");
        properties.setKeyStretchCount(3);
        passwordEncoder = new PasswordEncoder(properties);
    }


    @Test
    @DisplayName("같은 문자열을 암호화시 hash 결과가 같아야 한다")
    void encodeTest(){
        // given
        String text1 = "abcd";
        String text2 = "abcd";

        // when
        String encodedText1 = passwordEncoder.encode(text1);
        String encodedText2 = passwordEncoder.encode(text2);

        // then
        assertThat(encodedText1).isEqualTo(encodedText2);
    }

    @Test
    @DisplayName("다른 문자열을 암호화시 hash 결과가 달라야 한다")
    void encodeFailTest(){
        // given
        String text1 = "aaaaa";
        String text2 = "bbbbb";

        // when
        String encodedText1 = passwordEncoder.encode(text1);
        String encodedText2 = passwordEncoder.encode(text2);

        // then
        assertThat(encodedText1).isNotEqualTo(encodedText2);
    }

}