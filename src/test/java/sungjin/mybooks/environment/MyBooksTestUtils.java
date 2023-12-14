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


public class MyBooksTestUtils {

    private final static FixtureMonkey fixtureMonkey;

    static {
        fixtureMonkey = FixtureMonkey.builder()
                .objectIntrospector(FieldReflectionArbitraryIntrospector.INSTANCE)
                .plugin(new JakartaValidationPlugin())
                .build();
    }

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
                .setNotNull("email")
                .setNotNull("name")
                .set("name",Arbitraries.strings().ascii());
    }

    public static Book createBook() {
        return getBookBuilder().sample();
    }

    public static List<Book> createBooks(int count) {
        return getBookBuilder().sampleList(count);
    }

    public static List<Book> createBooks(int count,String titleContains) {
        return getBookBuilder()
                .set("title",Arbitraries.strings().withChars(titleContains))
                .sampleList(count);
    }

    private static ArbitraryBuilder<Book> getBookBuilder(){
        return fixtureMonkey.giveMeBuilder(Book.class)
                .setNull("id")
                .set("isbn", Arbitraries.strings().numeric().ofLength(13))
                .set("title",Arbitraries.strings().ascii());
    }


    public static Review createReview(User user, Book book) {
        return fixtureMonkey.giveMeBuilder(Review.class)
                .setNull("id")
                .set("user",user)
                .set("book",book)
                .set("content", Arbitraries.strings().ascii())
                .sample();
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
