package sungjin.mybooks.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sungjin.mybooks.domain.Session;
import sungjin.mybooks.domain.User;
import sungjin.mybooks.dto.request.Login;
import sungjin.mybooks.exception.InvalidLoginInformation;
import sungjin.mybooks.exception.NotFound;
import sungjin.mybooks.repository.SessionRepository;
import sungjin.mybooks.security.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;
    private final SessionRepository sessionRepository;

    @Transactional(readOnly = true)
    public void login(Login login){
        User user = userService.findUserByEmail(login.getEmail());
        String encodedPassword = passwordEncoder.encode(login.getPassword());
        if(!user.isCorrectPassword(encodedPassword)){
            throw new InvalidLoginInformation();
        }
    }

    @Transactional
    public Session createSession(User user){
        Session session = Session.builder()
                .accessToken(UUID.randomUUID().toString())
                .user(user)
                .build();
        return sessionRepository.save(session);
    }

    @Transactional
    public void removeSession(String accessToken){
        Session session = sessionRepository.findByAccessToken(accessToken)
                .orElseThrow(() -> new NotFound(Session.class, "access token", accessToken));
        sessionRepository.delete(session);
    }

    @Transactional(readOnly = true)
    public boolean isValidAccessToken(String accessToken){
        return sessionRepository.findByAccessToken(accessToken)
                .isPresent();
    }

    @Transactional(readOnly = true)
    public Optional<Session> findSession(String accessToken){
        return sessionRepository.findByAccessToken(accessToken);
    }


}
