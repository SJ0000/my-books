package sungjin.mybooks.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import sungjin.mybooks.MyBooksTestUtils;
import sungjin.mybooks.domain.Book;
import sungjin.mybooks.exception.NotFound;
import sungjin.mybooks.repository.BookRepository;
import sungjin.mybooks.search.BookSearchApi;
import sungjin.mybooks.search.BookSearchResult;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    BookRepository bookRepository;
    @Mock
    BookSearchApi bookSearchApi;
    @InjectMocks
    BookService bookService;

    @Test
    @DisplayName("id로 book 검색시, 해당하는 book이 없다면 NotFound Exception 발생")
    void findBookByIdNotExists() {
        given(bookRepository.findById(anyLong()))
                .willReturn(Optional.empty());

        assertThatThrownBy(() -> bookService.findBookById(1L))
                .isInstanceOf(NotFound.class);
    }

    @Test
    @DisplayName("외부 api로 검색했을 때 없는 책이라면 NotFound Exception 발생")
    void findOrCreateBook() {
        String isbn = "1234567890123";

        BookSearchResult documentEmptyResult = new BookSearchResult();
        documentEmptyResult.setDocuments(List.of());

        given(bookRepository.findByIsbn(isbn))
                .willReturn(Optional.empty());
        given(bookSearchApi.searchByIsbn(isbn))
                .willReturn(documentEmptyResult);
        assertThatThrownBy(()->bookService.findOrCreateBook(isbn))
                .isInstanceOf(NotFound.class);
    }
}