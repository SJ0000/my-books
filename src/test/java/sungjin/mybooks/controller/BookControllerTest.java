package sungjin.mybooks.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Controller Test는 통합테스트로 작성
 */

@SpringBootTest
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