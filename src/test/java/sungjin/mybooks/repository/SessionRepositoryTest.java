package sungjin.mybooks.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;
import sungjin.mybooks.MyBooksTestUtils;
import sungjin.mybooks.domain.Session;
import sungjin.mybooks.domain.User;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class SessionRepositoryTest {

    @Autowired
    SessionRepository sessionRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("access token으로 session을 조회")
    void findByAccessToken() throws Exception {
        // given
        String tokenName = "test-token";
        User user = MyBooksTestUtils.createUser();
        Session session = Session.builder()
                .accessToken(tokenName)
                .user(user)
                .build();

        sessionRepository.save(session);
        userRepository.save(user);

        // when
        Session findSession = sessionRepository.findByAccessToken(tokenName)
                .orElseThrow(() -> new RuntimeException("Session empty"));

        // then
        assertThat(findSession.getAccessToken()).isEqualTo(tokenName);
        assertThat(findSession.getUser().getEmail()).isEqualTo(user.getEmail());
        assertThat(findSession.getUser().getName()).isEqualTo(user.getName());
    }

    @Test
    @DisplayName("존재하지 않는 access token으로 session 조회시 empty optional 반환")
    void notExistAccessToken() throws Exception {
        // given
        String tokenName = "test-token";
        User user = MyBooksTestUtils.createUser();
        Session session = Session.builder()
                .accessToken(tokenName)
                .user(user)
                .build();
        sessionRepository.save(session);
        userRepository.save(user);

        // when
        Optional<Session> optionalSession = sessionRepository.findByAccessToken("not-exist-token");

        // then
        assertThat(optionalSession.isEmpty()).isTrue();
    }
}