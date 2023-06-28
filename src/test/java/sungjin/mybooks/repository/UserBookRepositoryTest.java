package sungjin.mybooks.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import sungjin.mybooks.domain.Book;
import sungjin.mybooks.domain.User;
import sungjin.mybooks.domain.UserBook;

import java.util.List;

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
    @DisplayName("")
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
        List<UserBook> mybook = userBookRepository.findAllByBookTitle(user.getId(), "mybook");


        // then
        System.out.println(mybook);
    }
}