package sungjin.mybooks.controller;

import org.assertj.core.api.HamcrestCondition;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.internal.hamcrest.HamcrestArgumentMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import sungjin.mybooks.domain.Book;
import sungjin.mybooks.domain.User;
import sungjin.mybooks.repository.BookRepository;
import sungjin.mybooks.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.*;
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
    UserRepository userRepository;

    @Autowired
    BookRepository bookRepository;

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
        mockMvc.perform(get("/books/{id}",bookId)
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
                        .queryParam("query",query)
                        .queryParam("page",String.valueOf(page))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}