package sungjin.mybooks.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sungjin.mybooks.domain.Comment;
import sungjin.mybooks.domain.Review;
import sungjin.mybooks.exception.NotFound;
import sungjin.mybooks.exception.Unauthorized;
import sungjin.mybooks.repository.CommentRepository;
import sungjin.mybooks.repository.ReviewRepository;
import sungjin.mybooks.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final ReviewRepository reviewRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;


    public void addComment(Long userId, Long reviewId, String content){
        if(!reviewRepository.existsById(reviewId))
            throw new NotFound(Review.class,"id",reviewId);
      Comment comment = Comment.builder()
                .review(reviewRepository.getReferenceById(reviewId))
                .user(userRepository.getReferenceById(userId))
                .content(content)
                .build();

        commentRepository.save(comment);
    }

    public void removeComment(Long userId, Long commentId){
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFound(Comment.class, "id", commentId));
        if(!comment.isOwner(userId))
            throw new Unauthorized();

        commentRepository.delete(comment);
    }
}
