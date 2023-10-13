package sungjin.mybooks.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import sungjin.mybooks.MyBooksTestUtils;
import sungjin.mybooks.config.PasswordEncoder;
import sungjin.mybooks.domain.User;
import sungjin.mybooks.dto.request.Login;
import sungjin.mybooks.exception.InvalidLoginInformation;
import sungjin.mybooks.exception.NotFound;
import sungjin.mybooks.repository.SessionRepository;

import java.util.Optional;

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
    @DisplayName("로그인 시 비밀번호가 일치하지 않을 경우 InvalidLoginInformation Exception 발생")
    void loginTest() throws Exception {
        // given
        String password = "raw password";
        User user = MyBooksTestUtils.createUser(password);

        BDDMockito.given(userService.findUserByEmail(Mockito.anyString()))
                .willReturn(user);

        // expected
        Assertions.assertThatThrownBy(()-> authService.login(new Login(user.getEmail(),"wrong password")))
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