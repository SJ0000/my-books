package sungjin.mybooks.environment.fixture.impl;

import com.navercorp.fixturemonkey.ArbitraryBuilder;
import sungjin.mybooks.domain.book.domain.Book;
import sungjin.mybooks.domain.review.domain.Review;
import sungjin.mybooks.domain.user.domain.User;
import sungjin.mybooks.environment.fixture.AbstractFixture;
import sungjin.mybooks.environment.fixture.Fixtures;

import static net.jqwik.api.Arbitraries.strings;

public class ReviewFixture extends AbstractFixture {

    private ReviewFixture() {
    }

    static {
        instance = new ReviewFixture();
    }

    public Review createReview() {
        return getReviewBuilder()
                .set("user", Fixtures.user().createUser())
                .set("book", Fixtures.book().createBook())
                .sample();
    }

    public Review createReview(User user, Book book) {
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
