package sungjin.mybooks.domain.common.domain.security.handler;

import lombok.RequiredArgsConstructor;
import sungjin.mybooks.domain.review.domain.Like;
import sungjin.mybooks.domain.review.repository.LikeRepository;
import sungjin.mybooks.domain.user.domain.User;
import sungjin.mybooks.global.exception.NotFound;
import sungjin.mybooks.global.exception.Unauthorized;

@RequiredArgsConstructor
public class LikeAuthorizeHandler implements DomainAuthorizeHandler {

    private final LikeRepository likeRepository;

    @Override
    public void authorize(User user, Long likeId) {
        Like like = likeRepository.findById(likeId)
                .orElseThrow(() -> new NotFound(Like.class, "id", likeId));

        if(!user.equals(like.getUser()))
            throw new Unauthorized();
    }
}
