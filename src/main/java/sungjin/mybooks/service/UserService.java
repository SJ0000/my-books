package sungjin.mybooks.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sungjin.mybooks.config.PasswordEncoder;
import sungjin.mybooks.domain.User;
import sungjin.mybooks.repository.UserRepository;
import sungjin.mybooks.request.SignUp;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = false)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Long signUpUser(SignUp join){

        if(existsUser(join.getEmail())){
            throw new RuntimeException("이미 존재하는 사용자입니다. email = " + join.getEmail());
        }

        String password = passwordEncoder.encode(join.getPassword());
        User user = User.builder()
                .email(join.getEmail())
                .name(join.getName())
                .password(password)
                .build();
        userRepository.save(user);
        return user.getId();
    }

    public User findUser(String email){
        return userRepository.findByEmail(email)
                .orElseThrow(()-> new RuntimeException("find user error. email = " + email));
    }

    public User findUser(Long id){
        return userRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("find user error. id = " + id));
    }

    private boolean existsUser(String email){
        return userRepository.findByEmail(email).isPresent();
    }

}
