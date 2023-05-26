package sungjin.mybooks.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sungjin.mybooks.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
