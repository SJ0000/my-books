package sungjin.mybooks.domain.user.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import sungjin.mybooks.domain.user.domain.Session;

@Repository
public interface SessionRepository extends CrudRepository<Session,String> {
}
