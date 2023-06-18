package sungjin.mybooks.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sungjin.mybooks.config.PasswordEncoder;
import sungjin.mybooks.domain.Session;
import sungjin.mybooks.domain.User;
import sungjin.mybooks.exception.InvalidLoginInformation;
import sungjin.mybooks.exception.NotFound;
import sungjin.mybooks.repository.SessionRepository;
import sungjin.mybooks.repository.UserRepository;

import javax.crypto.Cipher;
import java.security.MessageDigest;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final SessionRepository sessionRepository;

    public void validateUser(String email, String rawPassword){
        User user = userService.findUser(email);
        String encodedPassword = passwordEncoder.encode(rawPassword);

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

}
