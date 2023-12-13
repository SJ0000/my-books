package sungjin.mybooks.environment;

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


public class MyBooksTestUtils {

    private final static FixtureMonkey fixtureMonkey;

    static {
        fixtureMonkey = FixtureMonkey.builder()
                .objectIntrospector(FieldReflectionArbitraryIntrospector.INSTANCE)
                .plugin(new JakartaValidationPlugin())
                .build();
    }

    public static User createUser(String password) {
        return fixtureMonkey.giveMeBuilder(User.class)
                .setNull("id")
                .setNotNull("email")
                .setNotNull("name")
                .set("password",password)
                .sample();
    }

    public static User createUser() {
        return createUser(Arbitraries.strings().sample());
    }

    public static Book createBook() {
        Book sample = fixtureMonkey.giveMeBuilder(Book.class)
                .setNull("id")
                .set("isbn", Arbitraries.strings().numeric().ofLength(13))
                .sample();
        System.out.println("isbn " + sample.getIsbn());
        return sample;
    }

    public static List<Book> createBooks(int count) {
        return IntStream.range(1, count + 1).mapToObj((i) ->
                Book.builder()
                        .title("test book " + i)
                        .author("author1 author2")
                        .isbn("1234567890123")
                        .thumbnail("https://sub.domain.com/test" + i + ".jpg")
                        .build()
        ).collect(Collectors.toList());
    }

    public static Review createReview(User user, Book book, String content) {
        return Review.builder()
                .user(user)
                .book(book)
                .content(content)
                .build();
    }

    public static Comment createComment(User user, Review review, String content) {
        return Comment.builder()
                .user(user)
                .review(review)
                .content(content)
                .build();
    }

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
