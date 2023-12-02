package sungjin.mybooks.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sungjin.mybooks.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
