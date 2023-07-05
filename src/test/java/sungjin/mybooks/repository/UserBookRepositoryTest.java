package sungjin.mybooks.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;
import sungjin.mybooks.domain.BaseTimeEntity;
import sungjin.mybooks.domain.Book;
import sungjin.mybooks.domain.User;
import sungjin.mybooks.domain.UserBook;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserBookRepositoryTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    BookRepository bookRepository;
    @Autowired
    UserBookRepository userBookRepository;

    @Test
    @DisplayName("특정 사용자가 가진 책 리스트를 이름으로 검색하여 조회한다.")
    void findAllByBookTitleTest() throws Exception {
        // given
        User user = User.builder()
                .name("user")
                .email("alpha@beta.com")
                .build();

        userRepository.save(user);

        for (int i = 1; i <= 3; i++) {
            Book book = Book.builder()
                    .title("mybook" + i)
                    .build();
            bookRepository.save(book);

            UserBook userBook = UserBook.builder()
                    .user(user)
                    .book(book)
                    .build();
            userBookRepository.save(userBook);
        }

        // when
        Page<UserBook> result = userBookRepository.findAllByBookTitle(user.getId(), "mybook", PageRequest.of(0,10));

        // then
        assertThat(result.getTotalElements()).isEqualTo(3);
        assertThat(result.getTotalPages()).isEqualTo(1);
    }

    @Test
    @DisplayName("특정 사용자가 가진 책 리스트를 최신순으로 조회한다.")
    void findRecentUserBooksTest() throws Exception {
        // given
        User user = User.builder()
                .name("user")
                .email("alpha@beta.com")
                .build();

        userRepository.save(user);

        for (int i = 1; i <= 20; i++) {
            Book book = Book.builder()
                    .title("mybook" + i)
                    .build();
            bookRepository.save(book);

            UserBook userBook = UserBook.builder()
                    .user(user)
                    .book(book)
                    .build();
            ReflectionTestUtils.setField(userBook,"createdAt", LocalDateTime.of(2000,1,1,1,i));
            userBookRepository.save(userBook);
        }

        // when
        Page<UserBook> result = userBookRepository.findRecentUserBooks(user.getId(),  PageRequest.of(0,10));

        // then
        assertThat(result.getContent().get(0).getBook().getTitle()).isEqualTo("mybook20");
        assertThat(result.getContent()).isSortedAccordingTo((ub1,ub2)->{
            return ub2.getCreatedAt().compareTo(ub1.getCreatedAt());
        });
    }
}