package sungjin.mybooks.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sungjin.mybooks.domain.user.domain.User;
import sungjin.mybooks.domain.user.dto.Login;
import sungjin.mybooks.domain.user.dto.SignUp;
import sungjin.mybooks.domain.user.encrypt.PasswordEncoder;
import sungjin.mybooks.domain.user.repository.UserRepository;
import sungjin.mybooks.global.exception.AlreadyExists;
import sungjin.mybooks.global.exception.InvalidLoginInformation;
import sungjin.mybooks.global.exception.NotFound;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void signUpUser(SignUp join){
        if(existsUser(join.getEmail())){
            throw new AlreadyExists(User.class,"email",join.getEmail());
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
    public void validateForLogin(Login login) {
        User user = findUserByEmail(login.getEmail());
        String encodedPassword = passwordEncoder.encode(login.getPassword());
        if (!user.isCorrectPassword(encodedPassword)) {
            throw new InvalidLoginInformation();
        }
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
