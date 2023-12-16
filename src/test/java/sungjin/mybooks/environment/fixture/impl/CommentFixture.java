package sungjin.mybooks.environment.fixture.impl;

import com.navercorp.fixturemonkey.ArbitraryBuilder;
import org.springframework.test.util.ReflectionTestUtils;
import sungjin.mybooks.domain.review.domain.Comment;
import sungjin.mybooks.domain.review.domain.Review;
import sungjin.mybooks.domain.user.domain.User;
import sungjin.mybooks.environment.fixture.EntityFixture;
import sungjin.mybooks.environment.fixture.Fixtures;

public class CommentFixture extends EntityFixture<Comment> {

    @Override
    public Comment create() {
        return getBuilder()
                .set("user", Fixtures.user().create())
                .set("review",Fixtures.review().create())
                .sample();
    }

    public Comment create(User user, Review review) {
        Comment comment = getBuilder().sample();
        ReflectionTestUtils.setField(comment,"user",user);
        ReflectionTestUtils.setField(comment,"review",review);
        return comment;
    }

    private ArbitraryBuilder<Comment> getBuilder() {
        return fixtureMonkey.giveMeBuilder(Comment.class)
                .setNull("id");
    }
}
