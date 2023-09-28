package sungjin.mybooks;

import org.springframework.test.util.ReflectionTestUtils;
import sungjin.mybooks.domain.Book;
import sungjin.mybooks.domain.User;
import sungjin.mybooks.domain.Review;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class MyBooksTestUtils {

    public static User createUser(String password) {
        return User.builder()
                .name("test-user")
                .password(password)
                .email("abcd@efgh.com")
                .build();
    }

    public static User createUser() {
        return createUser("password");
    }

    public static Book createBook() {
        return Book.builder()
                .title("test book")
                .author("author1 author2")
                .isbn("1234567890123")
                .thumbnail("https://sub.domain.com/test.jpg")
                .build();
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

    public static LocalDateTime randomDateTime(){
        long bound = System.currentTimeMillis();
        long epochSecond = ThreadLocalRandom.current().nextLong(bound);
        return LocalDateTime.ofEpochSecond(epochSecond, 0, ZoneOffset.UTC);
    }

    public static String toFormData(Object object){
        return Arrays.stream(object.getClass().getDeclaredFields())
                .map(field -> field.getName() + "=" + ReflectionTestUtils.getField(object,field.getName()))
                .reduce((a, b) -> a + "&" + b)
                .orElseGet(() -> "");
    }
}
