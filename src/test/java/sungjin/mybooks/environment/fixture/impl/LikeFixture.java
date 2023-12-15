package sungjin.mybooks.environment.fixture.impl;

import sungjin.mybooks.domain.review.domain.Like;
import sungjin.mybooks.environment.fixture.EntityFixture;
import sungjin.mybooks.environment.fixture.Fixtures;

public class LikeFixture extends EntityFixture<Like> {

    @Override
    public Like create(){
        return fixtureMonkey.giveMeBuilder(Like.class)
                .setNull("id")
                .set("review", Fixtures.review().create())
                .set("user", Fixtures.user().create())
                .sample();
    }
}
