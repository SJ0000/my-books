package sungjin.mybooks.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sungjin.mybooks.domain.UserBook;

public interface UserBookRepository extends JpaRepository<UserBook, Long> {

}
