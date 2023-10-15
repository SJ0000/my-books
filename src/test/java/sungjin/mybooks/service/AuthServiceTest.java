package sungjin.mybooks.service;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import sungjin.mybooks.MyBooksTestUtils;
import sungjin.mybooks.config.PasswordEncoder;
import sungjin.mybooks.domain.Session;
import sungjin.mybooks.domain.User;
import sungjin.mybooks.dto.request.Login;
import sungjin.mybooks.exception.InvalidLoginInformation;
import sungjin.mybooks.exception.NotFound;
import sungjin.mybooks.repository.SessionRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    UserService userService;
    @Mock
    SessionRepository sessionRepository;
    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    AuthService authService;

    @Test
    @DisplayName("로그인 시 비밀번호가 일치하지 않을 경우 InvalidLoginInformation Exception 발생")
    void loginTest() throws Exception {
        // given
        String password = "password";
        User user = MyBooksTestUtils.createUser(password);

        BDDMockito.given(userService.findUserByEmail(Mockito.anyString()))
                .willReturn(user);

        BDDMockito.given(passwordEncoder.encode(Mockito.anyString()))
                .willAnswer((answer)->  answer.getArgument(0));

        // expected
        assertThatThrownBy(()-> authService.login(new Login(user.getEmail(),"wrong password")))
                .isInstanceOf(InvalidLoginInformation.class);
    }

    @Test
    @DisplayName("생성한 세션에는 argument로 전달된 User 값을 가지고 있어야 한다.")
    void createSessionTest(){
        //given
        BDDMockito.given(sessionRepository.save(BDDMockito.any(Session.class)))
                .willAnswer((answer)-> answer.getArgument(0));
        User user = MyBooksTestUtils.createUser();

        // when
        Session session = authService.createSession(user);

        // then
        assertThat(session.getUser()).isEqualTo(user);
    }

    @Test
    @DisplayName("삭제할 세션이 존재하지 않는 경우 NotFound Exception 발생")
    void removeSessionTest() throws Exception {
        // given
        BDDMockito.given(sessionRepository.findByAccessToken(Mockito.anyString()))
                .willReturn(Optional.empty());

        // expected
        assertThatThrownBy(()-> authService.removeSession("not exist session id"))
                .isInstanceOf(NotFound.class);
    }

}