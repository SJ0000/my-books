package sungjin.mybooks.environment.fixture.impl;

import sungjin.mybooks.domain.review.domain.Like;
import sungjin.mybooks.environment.fixture.AbstractFixture;
import sungjin.mybooks.environment.fixture.Fixtures;

public class LikeFixture extends AbstractFixture {
    private LikeFixture() {
    }

    static {
        instance = new LikeFixture();
    }

    public Like createLike(){
        return fixtureMonkey.giveMeBuilder(Like.class)
                .setNull("id")
                .set("review", Fixtures.review().createReview())
                .set("user", Fixtures.user().createUser())
                .sample();
    }
}
