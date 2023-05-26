package sungjin.mybooks.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sungjin.mybooks.domain.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {


}
