package sungjin.mybooks.environment;

import com.navercorp.fixturemonkey.ArbitraryBuilder;
import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.FieldReflectionArbitraryIntrospector;
import com.navercorp.fixturemonkey.jakarta.validation.plugin.JakartaValidationPlugin;
import net.jqwik.api.Arbitraries;
import org.springframework.test.util.ReflectionTestUtils;
import sungjin.mybooks.domain.book.domain.Book;
import sungjin.mybooks.domain.review.domain.Comment;
import sungjin.mybooks.domain.review.domain.Review;
import sungjin.mybooks.domain.user.domain.User;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static net.jqwik.api.Arbitraries.*;


public class MyBooksTestUtils {

    private final static FixtureMonkey fixtureMonkey;

    //region ctor
    static {
        fixtureMonkey = FixtureMonkey.builder()
                .objectIntrospector(FieldReflectionArbitraryIntrospector.INSTANCE)
                .plugin(new JakartaValidationPlugin())
                .build();
    }
    //endregion

    //region user
    public static User createUser(String password) {
        return getUserBuilder()
                .set("password", password)
                .sample();
    }

    public static User createUser() {
        return getUserBuilder()
                .sample();
    }

    private static ArbitraryBuilder<User> getUserBuilder() {
        return fixtureMonkey.giveMeBuilder(User.class)
                .setNull("id")
                .set("email", strings().ofMaxLength(255))
                .set("name",strings().ofMaxLength(255))
                .set("name", strings().ascii().ofMaxLength(255));
    }
    //endregion

    //region book
    public static Book createBook() {
        return getBookBuilder().sample();
    }

    public static List<Book> createBooks(int count) {
        return getBookBuilder().sampleList(count);
    }

    public static List<Book> createBooks(int count, String titleContains) {
        List<Book> books = getBookBuilder().sampleList(count);
        books.forEach(book -> {
            ReflectionTestUtils.setField(book, "title", book.getTitle() + titleContains);
        });
        return books;
    }

    private static ArbitraryBuilder<Book> getBookBuilder() {
        return fixtureMonkey.giveMeBuilder(Book.class)
                .setNull("id")
                .set("isbn", strings().numeric().ofLength(13))
                .set("title", strings().ascii().ofMaxLength(255));
    }
    //endregion

    //region review
    public static Review createReview() {
        return getReviewBuilder()
                .set("user", getUserBuilder().sample())
                .set("book", getBookBuilder().sample())
                .sample();
    }

    public static Review createReview(User user, Book book) {
        return getReviewBuilder()
                .set("user", user)
                .set("book", book)
                .sample();
    }

    public static ArbitraryBuilder<Review> getReviewBuilder() {
        return fixtureMonkey.giveMeBuilder(Review.class)
                .setNull("id")
                .set("content", strings().ofMinLength(256).ofMaxLength(1000));
    }

    //endregion

    //region comment
    public static Comment createComment(User user, Review review) {
        return fixtureMonkey.giveMeBuilder(Comment.class)
                .setNull("id")
                .set("user", user)
                .set("review", review)
                .sample();
    }
    //endregion

    public static LocalDateTime randomDateTime() {
        long bound = System.currentTimeMillis();
        long epochSecond = ThreadLocalRandom.current().nextLong(bound);
        return LocalDateTime.ofEpochSecond(epochSecond, 0, ZoneOffset.UTC);
    }

    public static String toFormData(Object object) {
        return Arrays.stream(object.getClass().getDeclaredFields())
                .map(field -> field.getName() + "=" + ReflectionTestUtils.getField(object, field.getName()))
                .reduce((a, b) -> a + "&" + b)
                .orElseGet(() -> "");
    }
}
