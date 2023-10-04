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
import sungjin.mybooks.domain.Review;
import sungjin.mybooks.dto.response.BookResponse;
import sungjin.mybooks.dto.response.PageInfo;
import sungjin.mybooks.dto.response.PageResponse;
import sungjin.mybooks.repository.ReviewRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
class BookServiceTest {

    @Autowired
    BookService bookService;

    @MockBean
    ReviewRepository userBookRepository;

}