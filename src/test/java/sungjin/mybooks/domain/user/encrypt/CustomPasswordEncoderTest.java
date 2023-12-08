package sungjin.mybooks.domain.user.encrypt;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import sungjin.mybooks.domain.user.encrypt.PasswordEncoderProperties;
import sungjin.mybooks.domain.user.encrypt.CustomPasswordEncoder;

import static org.assertj.core.api.Assertions.*;


class CustomPasswordEncoderTest {

    static CustomPasswordEncoder passwordEncoder;

    @BeforeAll
    static void init(){
        System.out.println("init");
        PasswordEncoderProperties properties = new PasswordEncoderProperties();
        properties.setSalt("test");
        properties.setKeyStretchCount(3);
        passwordEncoder = new CustomPasswordEncoder(properties);
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