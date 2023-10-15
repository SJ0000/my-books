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
import sungjin.mybooks.exception.NotFound;
import sungjin.mybooks.repository.BookRepository;
import sungjin.mybooks.search.BookSearchApi;

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
    void findBookByIdNotExists(){
        given(bookRepository.findById(anyLong()))
                .willReturn(Optional.empty());

        assertThatThrownBy(()->bookService.findBookById(1L))
                .isInstanceOf(NotFound.class);
    }

    @Test
    @DisplayName("isbn으로 book 검색시, 해당하는 book이")
    void test(){
        given(bookRepository.findById(anyLong()))
                .willReturn(Optional.empty());

        assertThatThrownBy(()->bookService.findBookById(1L))
                .isInstanceOf(NotFound.class);
    }




}