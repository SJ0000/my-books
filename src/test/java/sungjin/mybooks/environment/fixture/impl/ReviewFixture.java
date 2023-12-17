package sungjin.mybooks.environment.fixture.impl;

import com.navercorp.fixturemonkey.ArbitraryBuilder;
import net.jqwik.api.Arbitraries;
import org.aspectj.util.Reflection;
import org.springframework.test.util.ReflectionTestUtils;
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
        Review review = getReviewBuilder().sample();
        ReflectionTestUtils.setField(review,"user",user);
        ReflectionTestUtils.setField(review,"book",book);
        return review;
    }

    private ArbitraryBuilder<Review> getReviewBuilder() {
        return fixtureMonkey.giveMeBuilder(Review.class)
                .set("content", strings().ofMinLength(256).ofMaxLength(1000));
    }
}
