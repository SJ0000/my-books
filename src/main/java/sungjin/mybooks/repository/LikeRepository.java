package sungjin.mybooks.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sungjin.mybooks.domain.Like;

public interface LikeRepository extends JpaRepository<Like, Long> {
}
