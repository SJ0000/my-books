package sungjin.mybooks.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sungjin.mybooks.domain.User;
import sungjin.mybooks.exception.InvalidLoginInformation;
import sungjin.mybooks.repository.UserRepository;

import javax.crypto.Cipher;
import java.security.MessageDigest;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;


    public void validateUser(String email, String rawPassword){
        User user = userRepository.findByEmail(email);

        // 암호화 처리 필요
        if(!user.isCorrectPassword(rawPassword)){
            throw new InvalidLoginInformation();
        }

    }
}
