package sungjin.mybooks.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sungjin.mybooks.domain.Like;
import sungjin.mybooks.domain.Review;
import sungjin.mybooks.domain.User;
import sungjin.mybooks.exception.AlreadyExists;
import sungjin.mybooks.exception.NotFound;
import sungjin.mybooks.repository.LikeRepository;
import sungjin.mybooks.repository.ReviewRepository;
import sungjin.mybooks.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;

    @Transactional
    public void likeReview(Long userId, Long reviewId) {
        if (likeRepository.exists(userId, reviewId))
            throw new AlreadyExists(Like.class, "reviewId, userId", reviewId + " " + userId);

        Review review = reviewRepository.getReferenceById(reviewId);
        User user = userRepository.getReferenceById(userId);
        likeRepository.save(Like.builder()
                .user(user)
                .review(review)
                .build());
    }

    @Transactional
    public void cancelLikeReview(Long userId, Long reviewId) {
        Like like = likeRepository.findByUserIdAndReviewId(userId, reviewId)
                .orElseThrow(() -> new NotFound(Like.class, "reviewId, userId", reviewId + " " + userId));

        likeRepository.delete(like);
    }
}
