package sungjin.mybooks;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import sungjin.mybooks.config.PasswordEncoder;
import sungjin.mybooks.dto.request.SignUp;
import sungjin.mybooks.service.BookService;
import sungjin.mybooks.service.ReviewService;
import sungjin.mybooks.service.UserService;

@Component
@RequiredArgsConstructor
@Profile("debug")
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

        Long userId = userService.signUpUser(signup);

        reviewService.writeReview(userId, bookId,"123123123123123");
    }
}
