package sungjin.mybooks.domain.book.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import sungjin.mybooks.domain.book.repository.BookRepository;
import sungjin.mybooks.global.exception.NotFound;
import sungjin.mybooks.infra.search.BookSearchApi;
import sungjin.mybooks.infra.search.BookSearchResult;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.anyLong;
import static org.mockito.BDDMockito.given;


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
        given(bookSearchApi.request(Mockito.any()))
                .willReturn(documentEmptyResult);
        assertThatThrownBy(()->bookService.findOrCreateBook(isbn))
                .isInstanceOf(NotFound.class);
    }
}