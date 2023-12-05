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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import sungjin.mybooks.MyBooksTestUtils;
import sungjin.mybooks.domain.Book;
import sungjin.mybooks.domain.Session;
import sungjin.mybooks.domain.User;
import sungjin.mybooks.domain.Review;
import sungjin.mybooks.repository.BookRepository;
import sungjin.mybooks.repository.ReviewRepository;
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

    @Test
    @DisplayName("/book-search 호출시 query가 있으면 검색 결과와 페이지를 model로 전달해야 한다.")
    void searchBookHasQuery() throws Exception {
        mockMvc.perform(get("/book-search")
                        .queryParam("query", "dog"))
                .andExpect(model().attributeExists("books", "page"))
                .andExpect(view().name("book-search"));
    }

    @Test
    @DisplayName("/book-search 검색시 query가 없으면 빈 페이지를 전달한다")
    void searchBookQueryEmpty() throws Exception {
        mockMvc.perform(get("/book-search")
                        .queryParam("query", "dog"))
                .andExpect(view().name("book-search"));
    }
}