package sungjin.mybooks.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import sungjin.mybooks.domain.Book;
import sungjin.mybooks.domain.User;
import sungjin.mybooks.domain.UserBook;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Transactional
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
        Page<UserBook> result = userBookRepository.findAllByBookTitle(user.getId(), "mybook", PageRequest.of(1,10));

        // then
        assertThat(result.getTotalElements()).isEqualTo(3);
        assertThat(result.getTotalPages()).isEqualTo(1);

    }
}