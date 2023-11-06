package sungjin.mybooks.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import sungjin.mybooks.MyBooksTestUtils;
import sungjin.mybooks.security.CustomPasswordEncoder;
import sungjin.mybooks.domain.User;
import sungjin.mybooks.dto.request.SignUp;
import sungjin.mybooks.repository.UserRepository;
import sungjin.mybooks.security.PasswordEncoder;
import sungjin.mybooks.util.BypassPasswordEncoder;

import java.util.Optional;


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
}