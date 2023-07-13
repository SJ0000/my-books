package sungjin.mybooks.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import sungjin.mybooks.config.PasswordEncoder;
import sungjin.mybooks.domain.User;
import sungjin.mybooks.exception.InvalidLoginInformation;
import sungjin.mybooks.exception.NotFound;
import sungjin.mybooks.repository.SessionRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AuthServiceTest {

    @MockBean
    UserService userService;

    @MockBean
    SessionRepository sessionRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthService authService;

    @Test
    @DisplayName("비밀번호가 일치하지 않을 경우 InvalidLoginInformation Exception 발생")
    void validateUserTest() throws Exception {
        // given
        String email = "abcd@erfgh.com";
        String rawPassword = "1234";
        String encodedPassword = passwordEncoder.encode(rawPassword);
        User mockUser = User.builder()
                .email(email)
                .password(encodedPassword)
                .build();

        BDDMockito.given(userService.findUser(Mockito.anyString()))
                .willReturn(mockUser);

        // expected
        Assertions.assertThatThrownBy(()-> authService.validateUser(email,"wrong password"))
                .isInstanceOf(InvalidLoginInformation.class);
    }

    @Test
    @DisplayName("삭제할 세션이 존재하지 않는 경우 NotFound Exception 발생")
    void removeSessionTest() throws Exception {
        // given
        BDDMockito.given(sessionRepository.findByAccessToken(Mockito.anyString()))
                .willReturn(Optional.empty());

        // expected
        Assertions.assertThatThrownBy(()-> authService.removeSession("not exist session id"))
                .isInstanceOf(NotFound.class);
    }

}