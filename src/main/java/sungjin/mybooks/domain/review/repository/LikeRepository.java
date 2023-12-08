package sungjin.mybooks.domain.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sungjin.mybooks.domain.review.domain.Like;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {

    // native query 사용 이유 : JPQL은 select절에서 exists를 지원하지 않아서 다른 방식으로 우회해야 하는데, 깔끔하지 않다고 생각해서
    @Query(nativeQuery = true ,value = "SELECT EXISTS(SELECT * FROM likes l WHERE l.user_id = :userId AND l.review_Id = :reviewId)")
    boolean exists(Long userId, Long reviewId);

    Optional<Like> findByUserIdAndReviewId(Long userId, Long reviewId);
}
