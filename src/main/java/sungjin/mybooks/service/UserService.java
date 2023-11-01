package sungjin.mybooks.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sungjin.mybooks.domain.User;
import sungjin.mybooks.exception.AlreadyExistsException;
import sungjin.mybooks.exception.NotFound;
import sungjin.mybooks.repository.UserRepository;
import sungjin.mybooks.dto.request.SignUp;
import sungjin.mybooks.security.PasswordEncoder;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void signUpUser(SignUp join){
        if(existsUser(join.getEmail())){
            throw new AlreadyExistsException(User.class,"email",join.getEmail());
        }

        String password = passwordEncoder.encode(join.getPassword());
        User user = User.builder()
                .email(join.getEmail())
                .name(join.getName())
                .password(password)
                .build();
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public User findUserByEmail(String email){
        return userRepository.findByEmail(email)
                .orElseThrow(()-> new NotFound(User.class, "email", email));
    }

    @Transactional(readOnly = true)
    public User findUserById(Long id){
        return userRepository.findById(id)
                .orElseThrow(()-> new NotFound(User.class, "id", id));
    }

    private boolean existsUser(String email){
        return userRepository.findByEmail(email).isPresent();
    }

}
