package sungjin.mybooks.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import sungjin.mybooks.repository.BookRepository;
import sungjin.mybooks.repository.ReviewRepository;
import sungjin.mybooks.repository.UserRepository;
import sungjin.mybooks.service.AuthService;

/**
 * Controller Test는 통합테스트로 작성
 */

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class ReviewControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    UserRepository userRepository;
    @Autowired
    BookRepository bookRepository;
    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    AuthService authService;
    @Autowired
    ObjectMapper om;


}