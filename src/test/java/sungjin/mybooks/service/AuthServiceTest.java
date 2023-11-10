package sungjin.mybooks.service;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import sungjin.mybooks.MyBooksTestUtils;
import sungjin.mybooks.domain.Session;
import sungjin.mybooks.domain.User;
import sungjin.mybooks.dto.request.Login;
import sungjin.mybooks.exception.InvalidLoginInformation;
import sungjin.mybooks.exception.NotFound;
import sungjin.mybooks.repository.SessionRepository;
import sungjin.mybooks.security.PasswordEncoder;
import sungjin.mybooks.util.BypassPasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    SessionRepository sessionRepository;

    @InjectMocks
    AuthService authService;

    @Test
    @DisplayName("삭제할 세션이 존재하지 않는 경우 NotFound Exception 발생")
    void removeSessionTest() throws Exception {
        // given
        BDDMockito.given(sessionRepository.findById(Mockito.anyString()))
                .willReturn(Optional.empty());

        // expected
        assertThatThrownBy(()-> authService.removeSession("not exist session id"))
                .isInstanceOf(NotFound.class);
    }
}