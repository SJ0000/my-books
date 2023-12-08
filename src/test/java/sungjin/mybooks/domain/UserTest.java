package sungjin.mybooks.domain;

import org.junit.jupiter.api.Test;
import sungjin.mybooks.MyBooksTestUtils;
import sungjin.mybooks.domain.user.domain.User;

import static org.assertj.core.api.Assertions.*;

class UserTest {

    @Test
    void isCorrectPasswordTest(){
        // given
        String password = "password";
        User user = MyBooksTestUtils.createUser(password);

        // expected
        assertThat(user.isCorrectPassword(password)).isTrue();
        assertThat(user.isCorrectPassword("incorrect-password")).isFalse();
    }
}