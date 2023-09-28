package sungjin.mybooks.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;
import sungjin.mybooks.MyBooksTestUtils;
import sungjin.mybooks.domain.User;
import sungjin.mybooks.dto.request.SignUp;
import sungjin.mybooks.repository.UserRepository;

import java.util.Optional;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    UserService userService;

    @MockBean
    UserRepository userRepository;

    @Test
    @DisplayName("회원가입시 중복 이메일일 경우 RuntimeException 발생")
    void signUpEmailDuplicated() {
        // given
        User user = MyBooksTestUtils.createUser();
        BDDMockito.given(userRepository.findByEmail(Mockito.anyString()))
                .willReturn(Optional.of(user));

        // when, then
        SignUp newUser = SignUp.builder()
                .email(user.getEmail())
                .build();

        Assertions.assertThatThrownBy(() ->
                        userService.signUpUser(newUser))
                .isInstanceOf(RuntimeException.class);
    }
}