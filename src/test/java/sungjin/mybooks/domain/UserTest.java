package sungjin.mybooks.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import sungjin.mybooks.MyBooksTestUtils;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

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