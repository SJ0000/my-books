package sungjin.mybooks.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sungjin.mybooks.domain.Book;
import sungjin.mybooks.domain.Session;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {

}
