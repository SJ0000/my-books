package sungjin.mybooks.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sungjin.mybooks.domain.User;
import sungjin.mybooks.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User findUser(String email){
        return userRepository.findByEmail(email)
                .orElseThrow(()-> new RuntimeException("find user error. email = " + email));
    }


}
