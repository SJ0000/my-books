package sungjin.mybooks.domain.user.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import sungjin.mybooks.environment.MyBooksTestUtils;
import sungjin.mybooks.domain.user.dto.Login;
import sungjin.mybooks.global.exception.InvalidLoginInformation;
import sungjin.mybooks.domain.user.domain.User;
import sungjin.mybooks.domain.user.dto.SignUp;
import sungjin.mybooks.domain.user.repository.UserRepository;
import sungjin.mybooks.domain.user.encrypt.PasswordEncoder;
import sungjin.mybooks.environment.BypassPasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;


@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    UserRepository userRepository;
    @Spy
    PasswordEncoder passwordEncoder = new BypassPasswordEncoder();

    @InjectMocks
    UserService userService;

    @Test
    @DisplayName("회원가입시 중복 이메일일 경우 RuntimeException 발생")
    void signUpEmailDuplicated() {
        // given
        User user = MyBooksTestUtils.createUser();
        BDDMockito.given(userRepository.findByEmail(Mockito.anyString()))
                .willReturn(Optional.of(user));

        // when, then
        SignUp newUser = SignUp.builder()
                .email(user.getEmail())
                .build();

        Assertions.assertThatThrownBy(() ->
                        userService.signUpUser(newUser))
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    @DisplayName("로그인 시 비밀번호가 일치하지 않을 경우 InvalidLoginInformation Exception 발생")
    void validateForLoginTest() throws Exception {
        // given
        String password = "password";
        User user = MyBooksTestUtils.createUser(password);

        BDDMockito.given(userRepository.findByEmail(Mockito.anyString()))
                .willReturn(Optional.of(user));

        // expected
        assertThatThrownBy(()-> userService.validateForLogin(new Login(user.getEmail(),"wrong password")))
                .isInstanceOf(InvalidLoginInformation.class);
    }
}