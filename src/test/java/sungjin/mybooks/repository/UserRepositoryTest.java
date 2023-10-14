package sungjin.mybooks.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import sungjin.mybooks.MyBooksTestUtils;
import sungjin.mybooks.domain.User;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("이메일로 특정 사용자 조회")
    void findByEmailTest() throws Exception {
        // given
        User user = MyBooksTestUtils.createUser();
        userRepository.save(user);

        // when
        Optional<User> optionalUser = userRepository.findByEmail(user.getEmail());

        // then
        assertThat(optionalUser.isPresent()).isTrue();
        User findUser = optionalUser.get();
        assertThat(user).isEqualTo(findUser);
    }
}