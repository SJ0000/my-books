package sungjin.mybooks.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sungjin.mybooks.domain.User;
import sungjin.mybooks.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User findUser(Long id){
        return userRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("find user error. id = " + id));
    }


}
