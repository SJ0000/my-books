package sungjin.mybooks.environment.fixture.impl;

import sungjin.mybooks.domain.review.domain.Comment;
import sungjin.mybooks.domain.review.domain.Review;
import sungjin.mybooks.domain.user.domain.User;
import sungjin.mybooks.environment.fixture.AbstractFixture;

public class CommentFixture extends AbstractFixture {

    private CommentFixture() {
    }

    static {
        instance = new CommentFixture();
    }

    public Comment createComment(User user, Review review) {
        return fixtureMonkey.giveMeBuilder(Comment.class)
                .setNull("id")
                .set("user", user)
                .set("review", review)
                .sample();
    }
}
