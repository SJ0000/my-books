package sungjin.mybooks.service;

import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sungjin.mybooks.config.PasswordEncoder;
import sungjin.mybooks.domain.User;
import sungjin.mybooks.repository.UserRepository;
import sungjin.mybooks.request.Join;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = false)
public class UserService {

    private final UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Transactional
    public Long joinUser(Join join){

        if(existsUser(join.getEmail())){
            throw new RuntimeException("이미 존재하는 사용자입니다. email = " + join.getEmail());
        }

        String password = passwordEncoder.encode(join.getRawPassword());
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

    private boolean existsUser(String email){
        return userRepository.findByEmail(email).isPresent();
    }

}
