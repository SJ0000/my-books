package sungjin.mybooks.domain.user.service;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import sungjin.mybooks.domain.user.domain.Session;
import sungjin.mybooks.domain.user.repository.SessionRepository;
import sungjin.mybooks.global.exception.NotFound;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final SessionRepository sessionRepository;

    @Value("${auth.session.time-to-live-seconds}")
    private Long timeToLiveSeconds;

    public Session createSession(Long userId) {
        Session session = new Session(UUID.randomUUID().toString(), userId, timeToLiveSeconds);
        return sessionRepository.save(session);
    }

    public void removeSession(String accessToken) {
        Session session = sessionRepository.findById(accessToken)
                .orElseThrow(() -> new NotFound(Session.class, "access token", accessToken));
        sessionRepository.delete(session);
    }

    public boolean isValidAccessToken(String accessToken) {
        return sessionRepository.findById(accessToken)
                .isPresent();
    }

    public Optional<Session> findSession(String accessToken) {
        return sessionRepository.findById(accessToken);
    }

}
