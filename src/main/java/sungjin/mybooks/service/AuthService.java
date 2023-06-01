package sungjin.mybooks.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sungjin.mybooks.config.PasswordEncoder;
import sungjin.mybooks.domain.User;
import sungjin.mybooks.exception.InvalidLoginInformation;
import sungjin.mybooks.repository.UserRepository;

import javax.crypto.Cipher;
import java.security.MessageDigest;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public void validateUser(String email, String rawPassword){
        User user = userService.findUser(email);
        String encodedPassword = passwordEncoder.encode(rawPassword);

        if(!user.isCorrectPassword(encodedPassword)){
            throw new InvalidLoginInformation();
        }
    }
}
