package sungjin.mybooks.environment.fixture.impl;

import com.navercorp.fixturemonkey.ArbitraryBuilder;
import sungjin.mybooks.domain.book.domain.Book;
import sungjin.mybooks.domain.review.domain.Review;
import sungjin.mybooks.domain.user.domain.User;
import sungjin.mybooks.environment.fixture.EntityFixture;
import sungjin.mybooks.environment.fixture.Fixtures;

import static net.jqwik.api.Arbitraries.strings;

public class ReviewFixture extends EntityFixture<Review> {

    @Override
    public Review create() {
        return getReviewBuilder()
                .set("user", Fixtures.user().create())
                .set("book", Fixtures.book().create())
                .sample();
    }

    public Review create(User user, Book book) {
        return getReviewBuilder()
                .set("user", user)
                .set("book", book)
                .sample();
    }

    private ArbitraryBuilder<Review> getReviewBuilder() {
        return fixtureMonkey.giveMeBuilder(Review.class)
                .setNull("id")
                .set("content", strings().ofMinLength(256).ofMaxLength(1000));
    }
}
