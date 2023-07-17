package sungjin.mybooks.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import sungjin.mybooks.domain.Book;
import sungjin.mybooks.domain.User;
import sungjin.mybooks.domain.UserBook;
import sungjin.mybooks.dto.response.BookInfo;
import sungjin.mybooks.dto.response.PageInfo;
import sungjin.mybooks.dto.response.PageResponse;
import sungjin.mybooks.repository.UserBookRepository;

import java.util.ArrayList;
import java.util.List;

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
        User mockUser = User.builder()
                .build();

        List<UserBook> content = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            Book book = Book.builder()
                    .title("book " + i)
                    .build();
            content.add(
                    UserBook.builder()
                            .user(mockUser)
                            .book(book)
                            .build()
            );
        }

        Page<UserBook> page = new PageImpl(content, PageRequest.of(0,10),4);
        BDDMockito.given(userBookRepository.findRecentUserBooks(Mockito.anyLong(), Mockito.any()))
                .willReturn(page);

        // when
        PageResponse<BookInfo> result = bookService.searchRecentUserbooks(1L, 1);

        // then
        List<BookInfo> data = result.getData();
        PageInfo pageInfo = result.getPageInfo();
        assertThat(data.size()).isEqualTo(4);
        assertThat(pageInfo.getTotalPage()).isEqualTo(1);
        assertThat(pageInfo.getTotalElements()).isEqualTo(4);
        assertThat(pageInfo.isLast()).isEqualTo(true);
    }
}