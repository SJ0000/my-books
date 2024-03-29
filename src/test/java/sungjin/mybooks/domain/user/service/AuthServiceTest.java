package sungjin.mybooks.domain.user.service;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import sungjin.mybooks.domain.user.repository.SessionRepository;
import sungjin.mybooks.global.exception.NotFound;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

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