package sungjin.mybooks.service;

import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import sungjin.mybooks.MyBooksTestUtils;
import sungjin.mybooks.domain.Book;
import sungjin.mybooks.domain.User;
import sungjin.mybooks.domain.UserBook;
import sungjin.mybooks.dto.response.BookResponse;
import sungjin.mybooks.dto.response.PageInfo;
import sungjin.mybooks.dto.response.PageResponse;
import sungjin.mybooks.repository.UserBookRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
class BookServiceTest {

    @Autowired
    BookService bookService;

    @MockBean
    UserBookRepository userBookRepository;

    @Test
    void searchRecentUserbooksTest() throws Exception {
        // given
        User user = MyBooksTestUtils.createUser();
        List<Book> books = MyBooksTestUtils.createBooks(4);

        List<UserBook> userBooks = books.stream().map(book ->
                MyBooksTestUtils.createUserBook(user, book)
        ).toList();

        Page<UserBook> page = new PageImpl(userBooks, PageRequest.of(0,10),4);
        BDDMockito.given(userBookRepository.findRecentUserBooks(Mockito.anyLong(), Mockito.any()))
                .willReturn(page);

        // when
        PageResponse<BookResponse> result = bookService.searchRecentUserbooks(1L, 1);

        // then
        List<BookResponse> data = result.getData();
        PageInfo pageInfo = result.getPageInfo();
        assertThat(data.size()).isEqualTo(4);
        assertThat(pageInfo.getTotalPage()).isEqualTo(1);
        assertThat(pageInfo.getTotalElements()).isEqualTo(4);
        assertThat(pageInfo.isLast()).isEqualTo(true);
    }
}