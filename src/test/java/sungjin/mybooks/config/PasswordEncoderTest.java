package sungjin.mybooks.config;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class PasswordEncoderTest {


    @Test
    @DisplayName("PasswordEncoder 클래스의 encode 동작을 테스트한다")
    void encodeTest(){
        // given
        String text = "abcd";
        String correctResult = "88d4266fd4e6338d13b845fcf289579d209c897823b9217da3e161936f031589";

        // when
        PasswordEncoder passwordEncoder = new PasswordEncoder("", 1);
        String encodedText = passwordEncoder.encode(text);

        // then
        assertThat(encodedText).isEqualTo(correctResult);
    }

}