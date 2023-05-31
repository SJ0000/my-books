package sungjin.mybooks.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import sungjin.mybooks.domain.User;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("findByEmail 호출 쿼리 테스트")
    void findByEmailTest() throws Exception {
        // given
        String email = "abcd@gmail.com";
        User user = User.builder()
                .email(email)
                .name("sungjin")
                .password("aa")
                .build();
        userRepository.save(user);

        // when
        User findUser = userRepository.findByEmail(email);

        // then
        assertThat(user).isEqualTo(findUser);
    }
}