package sungjin.mybooks.domain.review.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.util.ReflectionTestUtils;
import sungjin.mybooks.domain.book.service.BookService;
import sungjin.mybooks.domain.common.model.PageModel;
import sungjin.mybooks.domain.review.domain.Review;
import sungjin.mybooks.domain.review.model.ReviewModel;
import sungjin.mybooks.domain.review.repository.ReviewRepository;
import sungjin.mybooks.domain.user.domain.User;
import sungjin.mybooks.domain.user.service.UserService;
import sungjin.mybooks.environment.MyBooksTestUtils;
import sungjin.mybooks.global.exception.Unauthorized;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.anyLong;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {

    @Mock
    UserService userService;
    @Mock
    BookService bookService;
    @Mock
    ReviewRepository reviewRepository;
    @InjectMocks
    ReviewService reviewService;

    @Test
    @DisplayName("특정 사용자의 리뷰를 최신순으로 조회한 결과를 dto 형태로 반환")
    void findRecentReviewsTest() throws Exception {
        // given
        int currentPage = 0;
        int totalCount = 11;
        int pageSize = 10;
        ReflectionTestUtils.setField(reviewService,"pageSize",pageSize);
        given(reviewRepository.findRecentReviews(Mockito.anyLong(),Mockito.any(PageRequest.class)))
                .willAnswer((answer)->{
                    List<Review> reviews = IntStream.range(1, 11)
                            .mapToObj((i) -> MyBooksTestUtils.createReview(
                                    MyBooksTestUtils.createUser(),
                                    MyBooksTestUtils.createBook(),
                                    "content" + i))
                            .toList();
                    return new PageImpl<>(reviews,PageRequest.of(currentPage,pageSize),totalCount);
                });

        // when
        PageModel<ReviewModel> result = reviewService.findRecentReviews(1L, 0);

        // then
        assertThat(result.getPageInfo().getTotalPage()).isEqualTo(2);
        assertThat(result.getPageInfo().getCurrentPage()).isEqualTo(0);
        assertThat(result.getData().size()).isEqualTo(10);
    }

    @Test
    @DisplayName("특정 사용자의 리뷰를 책 제목으로 검색한 결과를 dto 형태로 반환")
    void findReviewsByBookTitleTest() throws Exception {
        // given
        int currentPage = 0;
        int totalCount = 11;
        int pageSize = 10;
        ReflectionTestUtils.setField(reviewService,"pageSize",pageSize);
        given(reviewRepository.findRecentReviews(Mockito.anyLong(),Mockito.any(PageRequest.class)))
                .willAnswer((answer)->{
                    List<Review> reviews = IntStream.range(1, 11)
                            .mapToObj((i) -> MyBooksTestUtils.createReview(
                                    MyBooksTestUtils.createUser(),
                                    MyBooksTestUtils.createBook(),
                                    "content" + i))
                            .toList();
                    return new PageImpl<>(reviews,PageRequest.of(currentPage,pageSize),totalCount);
                });

        // when
        PageModel<ReviewModel> result = reviewService.findRecentReviews(1L, 0);

        // then
        assertThat(result.getPageInfo().getTotalPage()).isEqualTo(2);
        assertThat(result.getPageInfo().getCurrentPage()).isEqualTo(0);
        assertThat(result.getData().size()).isEqualTo(10);
    }

    @Test
    @DisplayName("리뷰의 소유가 리뷰를 수정할 경우, 수정이 되어야 한다.")
    void editReviewTest(){
        // given
        long userId = 1L;
        User user = MyBooksTestUtils.createUser();
        ReflectionTestUtils.setField(user,"id",userId);
        Review review = MyBooksTestUtils.createReview(
                user, MyBooksTestUtils.createBook(),"review contents");

        given(reviewRepository.findById(anyLong()))
                .willReturn(Optional.of(review));

        String newContent = "new content";

        // when
        reviewService.editReview(1L,userId, newContent);

        // then
        assertThat(review.getContent()).isEqualTo(newContent);
    }

    @Test
    @DisplayName("리뷰의 소유자가 아닌데 수정을 시도할 경우 Unauthorized Exception 발생")
    void editReviewNotOwnerTest(){
        long userId = 1L;
        User user = MyBooksTestUtils.createUser();
        ReflectionTestUtils.setField(user,"id",userId);
        Review review = MyBooksTestUtils.createReview(
                user, MyBooksTestUtils.createBook(),"review contents");

        given(reviewRepository.findById(anyLong()))
                .willReturn(Optional.of(review));

        assertThatThrownBy(()->reviewService.editReview(1L,userId+1,"edit review contents"))
                .isInstanceOf(Unauthorized.class);
    }
}