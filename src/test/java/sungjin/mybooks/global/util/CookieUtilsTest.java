package sungjin.mybooks.global.util;

import jakarta.servlet.http.Cookie;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import sungjin.mybooks.global.util.CookieUtils;

import java.util.Optional;

class CookieUtilsTest {


    @Test
    @DisplayName("쿠키 배열에서 특정 이름을 가진 쿠키 값을 가져온다")
    void findCookieTest() throws Exception {
        // given
        Cookie[] cookies = new Cookie[]{
                new Cookie("id","1234"),
                new Cookie("mode","auto")
        };

        // when
        Optional<String> value = CookieUtils.getCookieValue(cookies, "id");

        // then
        Assertions.assertThat(value.isPresent()).isTrue();
        Assertions.assertThat(value.get()).isEqualTo("1234");
    }

    @Test
    @DisplayName("찾는 쿠키가 존재하지 않는 경우 비어있는 optional을 반환한다")
    void findCookieNotExistTest() throws Exception {
        // given
        Cookie[] cookies = new Cookie[]{
              new Cookie("mode","auto")
        };

        // when
        Optional<String> id = CookieUtils.getCookieValue(cookies, "id");

        // then
        Assertions.assertThat(id.isEmpty()).isTrue();
    }
}