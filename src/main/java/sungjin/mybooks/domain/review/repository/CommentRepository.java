package sungjin.mybooks.domain.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sungjin.mybooks.domain.review.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
