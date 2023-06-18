package sungjin.mybooks.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import sungjin.mybooks.domain.User;
import sungjin.mybooks.request.SignUp;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    UserService userService;

    @Test
    @DisplayName("이미 존재하는 이메일일 경우 회원가입이 불가능해야한다.")
    void joinExistsEmail() {
        // given
        String email = "alpha@beta.com";
        SignUp user = SignUp.builder()
                .email(email)
                .build();

        userService.signUpUser(user);

        // when, then
        SignUp newUser = SignUp.builder()
                .email(email)
                .build();

        Assertions.assertThatThrownBy(() ->
                        userService.signUpUser(newUser))
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    @DisplayName("회원가입 시 비밀번호를 암호화하야 저장한다.")
    void joinPasswordEncrypt() {
        // given
        String email = "alpha@beta.com";
        String rawPassword = "abcdefg";
        SignUp join = SignUp.builder()
                .email(email)
                .password(rawPassword)
                .build();

        // when
        Long userId = userService.signUpUser(join);
        User user = userService.findUser(userId);

        // then
        Assertions.assertThat(user.getPassword())
                .isNotEqualTo(rawPassword);
    }
}