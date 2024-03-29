package sungjin.mybooks.domain.user.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import sungjin.mybooks.domain.user.domain.User;
import sungjin.mybooks.environment.fixture.Fixtures;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("이메일로 특정 사용자 조회")
    void findByEmailTest() throws Exception {
        // given
        User user = Fixtures.user().create();
        userRepository.save(user);

        // when
        Optional<User> optionalUser = userRepository.findByEmail(user.getEmail());

        // then
        assertThat(optionalUser.isPresent()).isTrue();
        User findUser = optionalUser.get();
        assertThat(user).isEqualTo(findUser);
    }
}