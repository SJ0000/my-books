package sungjin.mybooks.domain.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sungjin.mybooks.domain.common.annotation.DomainAuthorize;
import sungjin.mybooks.domain.common.annotation.DomainId;
import sungjin.mybooks.domain.review.domain.Comment;
import sungjin.mybooks.domain.review.domain.Like;
import sungjin.mybooks.domain.review.domain.Review;
import sungjin.mybooks.domain.review.repository.LikeRepository;
import sungjin.mybooks.domain.review.repository.ReviewRepository;
import sungjin.mybooks.domain.user.domain.User;
import sungjin.mybooks.domain.user.repository.UserRepository;
import sungjin.mybooks.global.exception.AlreadyExists;
import sungjin.mybooks.global.exception.NotFound;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;

    @Transactional
    public void likeReview(Long userId, Long reviewId) {
        if (likeRepository.exists(userId, reviewId))
            throw new AlreadyExists(Like.class, "reviewId, userId", reviewId + ", " + userId);

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
