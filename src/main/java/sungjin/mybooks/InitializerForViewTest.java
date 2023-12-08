package sungjin.mybooks;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import sungjin.mybooks.domain.user.domain.User;
import sungjin.mybooks.domain.user.dto.SignUp;
import sungjin.mybooks.domain.book.service.BookService;
import sungjin.mybooks.domain.review.service.ReviewService;
import sungjin.mybooks.domain.user.service.UserService;

@Component
@RequiredArgsConstructor
@Profile("local")
public class InitializerForViewTest {

    private final BookService bookService;
    private final UserService userService;
    private final ReviewService reviewService;

    @PostConstruct
    public void initialize() {
        Long bookId = bookService.createBook("9791158391409");
        SignUp signup = SignUp.builder()
                .email("123@123.com")
                .name("123")
                .password("123")
                .build();

        userService.signUpUser(signup);
        User user = userService.findUserByEmail(signup.getEmail());

        reviewService.writeReview(user.getId(), bookId,"123123123123123");
    }
}
