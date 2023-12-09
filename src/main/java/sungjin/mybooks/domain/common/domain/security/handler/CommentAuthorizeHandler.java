package sungjin.mybooks.domain.common.domain.security.handler;

import lombok.RequiredArgsConstructor;
import sungjin.mybooks.domain.review.domain.Comment;
import sungjin.mybooks.domain.review.repository.CommentRepository;
import sungjin.mybooks.domain.user.domain.User;
import sungjin.mybooks.global.exception.NotFound;
import sungjin.mybooks.global.exception.Unauthorized;

@RequiredArgsConstructor
public class CommentAuthorizeHandler implements DomainAuthorizeHandler {

    private final CommentRepository commentRepository;

    @Override
    public void authorize(User user, Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFound(Comment.class, "id", commentId));

        if(!user.equals(comment.getUser()))
            throw new Unauthorized();
    }
}
