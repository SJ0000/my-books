package sungjin.mybooks.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import sungjin.mybooks.domain.user.domain.Session;

import java.time.LocalDate;

class SessionTest {

    @Test
    @DisplayName("Session의 만료기한 날짜 비교")
    void sessionCreateTest(){
        Long timeToLiveSeconds = 24 * 60 * 60L;
        Session session = new Session("session-id", 1L, timeToLiveSeconds);
        LocalDate tomorrow = LocalDate.now().plusDays(1L);
        Assertions.assertThat(session.getExpireDate().toLocalDate()).isEqualTo(tomorrow);
    }
}