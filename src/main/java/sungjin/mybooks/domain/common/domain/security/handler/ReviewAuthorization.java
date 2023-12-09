package sungjin.mybooks.domain.common.domain.security.handler;

import lombok.RequiredArgsConstructor;
import sungjin.mybooks.domain.review.domain.Review;
import sungjin.mybooks.domain.review.repository.ReviewRepository;
import sungjin.mybooks.domain.user.domain.User;
import sungjin.mybooks.global.exception.NotFound;
import sungjin.mybooks.global.exception.Unauthorized;

@RequiredArgsConstructor
public class ReviewAuthorization implements DomainAuthorizationHandler {

    private final ReviewRepository reviewRepository;

    @Override
    public void authorize(User user, Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new NotFound(Review.class, "id", reviewId));

        if(!user.equals(review.getUser()))
            throw new Unauthorized();
    }
}
