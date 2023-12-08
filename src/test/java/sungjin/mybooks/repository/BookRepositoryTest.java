package sungjin.mybooks.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import sungjin.mybooks.MyBooksTestUtils;
import sungjin.mybooks.domain.book.domain.Book;
import sungjin.mybooks.domain.book.repository.BookRepository;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
class BookRepositoryTest {

    @Autowired
    BookRepository bookRepository;

    @Test
    @DisplayName("isbn으로 도서를 조회한다.")
    void findByIsbnTest(){
        Book book = MyBooksTestUtils.createBook();
        String isbn = book.getIsbn();
        bookRepository.save(book);

        Book result = bookRepository.findByIsbn(isbn).get();
        assertThat(result).isEqualTo(book);
    }
}