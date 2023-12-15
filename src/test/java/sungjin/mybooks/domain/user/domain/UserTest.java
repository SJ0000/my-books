package sungjin.mybooks.domain.user.domain;

import org.junit.jupiter.api.Test;
import sungjin.mybooks.environment.fixture.Fixtures;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {

    @Test
    void isCorrectPasswordTest(){
        // given
        String password = "password";
        User user = Fixtures.user().create(password);

        // expected
        assertThat(user.isCorrectPassword(password)).isTrue();
        assertThat(user.isCorrectPassword("incorrect-password")).isFalse();
    }
}