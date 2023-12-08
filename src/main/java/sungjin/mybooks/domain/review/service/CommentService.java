package sungjin.mybooks.domain.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sungjin.mybooks.domain.review.domain.Comment;
import sungjin.mybooks.domain.review.domain.Review;
import sungjin.mybooks.global.exception.NotFound;
import sungjin.mybooks.global.exception.Unauthorized;
import sungjin.mybooks.domain.review.repository.CommentRepository;
import sungjin.mybooks.domain.review.repository.ReviewRepository;
import sungjin.mybooks.domain.user.repository.UserRepository;

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
