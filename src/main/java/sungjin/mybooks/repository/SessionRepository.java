package sungjin.mybooks.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import sungjin.mybooks.domain.Book;
import sungjin.mybooks.domain.Session;

import java.util.Optional;

@Repository
public interface SessionRepository extends CrudRepository<Session,String> {
}
