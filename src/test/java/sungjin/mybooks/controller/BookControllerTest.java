package sungjin.mybooks.controller;

import jakarta.servlet.http.Cookie;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.transaction.annotation.Transactional;
import sungjin.mybooks.domain.Book;
import sungjin.mybooks.domain.Session;
import sungjin.mybooks.domain.User;
import sungjin.mybooks.domain.UserBook;
import sungjin.mybooks.repository.BookRepository;
import sungjin.mybooks.repository.UserBookRepository;
import sungjin.mybooks.repository.UserRepository;
import sungjin.mybooks.service.AuthService;
import sungjin.mybooks.util.CookieNames;
import java.util.stream.IntStream;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Controller Test는 통합테스트로 작성
 */

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class BookControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    AuthService authService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    UserBookRepository userBookRepository;

    @Test
    @DisplayName("개별 도서 조회")
    void getBookTest() throws Exception {
        // given
        Book book = Book.builder()
                .title("test book")
                .author("author1 author2")
                .isbn("1234567890123")
                .thumbnail("https://localhost:8080/sample1.jpg")
                .build();
        bookRepository.save(book);
        Long bookId = book.getId();

        // expected
        mockMvc.perform(get("/books/{id}", bookId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(book.getTitle()))
                .andExpect(jsonPath("$.authors").isArray())
                .andExpect(jsonPath("$.authors", Matchers.hasItems("author1", "author2")))
                .andExpect(jsonPath("$.isbn").value(book.getIsbn()));
    }

    @Test
    @DisplayName("도서 검색")
    void searchBookTest() throws Exception {
        // given
        String query = "dog";
        int page = 1;

        // expected
        mockMvc.perform(get("/search/book")
                        .queryParam("query", query)
                        .queryParam("page", String.valueOf(page))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("특정 사용자의 도서 리스트 조회")
    void getUserBooksTest() throws Exception {
        // given
        User user = User.builder()
                .name("user")
                .email("abcd@efgh.com")
                .password("1234")
                .build();
        userRepository.save(user);
        Session session = authService.createSession(user);

        IntStream.range(1, 21).forEach(
                i -> {
                    Book book = Book.builder()
                            .title("book 1")
                            .isbn("9782313456451")
                            .build();
                    bookRepository.save(book);
                    UserBook userBook = new UserBook(user, book);
                    userBookRepository.save(userBook);
                }
        );

        // expected
        mockMvc.perform(get("/users/books")
                        .cookie(new Cookie(CookieNames.SESSION_ID, session.getAccessToken()))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data",Matchers.hasSize(10)))
                .andExpect(jsonPath("$.pageInfo.totalElements").value(20))
                .andExpect(jsonPath("$.pageInfo.totalPage").value(2))
                .andExpect(jsonPath("$.pageInfo.last").value(false))
                .andDo(MockMvcResultHandlers.print());
    }

}