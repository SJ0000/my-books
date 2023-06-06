package sungjin.mybooks.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;
import sungjin.mybooks.config.AuthConfig;
import sungjin.mybooks.domain.User;
import sungjin.mybooks.repository.UserRepository;
import sungjin.mybooks.request.Join;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

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
        Join user = Join.builder()
                .email(email)
                .build();

        userService.joinUser(user);

        // when, then
        Join newUser = Join.builder()
                .email(email)
                .build();

        Assertions.assertThatThrownBy(() ->
                        userService.joinUser(newUser))
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    @DisplayName("회원가입 시 비밀번호를 암호화하야 저장한다.")
    void joinPasswordEncrypt() {
        // given
        String email = "alpha@beta.com";
        String rawPassword = "abcdefg";
        Join join = Join.builder()
                .email(email)
                .rawPassword(rawPassword)
                .build();

        // when
        Long userId = userService.joinUser(join);
        User user = userService.findUser(userId);

        // then
        Assertions.assertThat(user.getPassword())
                .isNotEqualTo(rawPassword);
    }
}