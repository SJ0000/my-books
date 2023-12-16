package sungjin.mybooks.domain.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import sungjin.mybooks.domain.user.domain.Session;
import sungjin.mybooks.domain.user.domain.User;
import sungjin.mybooks.domain.user.dto.Login;
import sungjin.mybooks.domain.user.dto.SignUp;
import sungjin.mybooks.domain.user.encrypt.PasswordEncoder;
import sungjin.mybooks.domain.user.repository.SessionRepository;
import sungjin.mybooks.domain.user.repository.UserRepository;
import sungjin.mybooks.domain.user.service.AuthService;
import sungjin.mybooks.environment.MyBooksTestUtils;
import sungjin.mybooks.environment.fixture.Fixtures;
import sungjin.mybooks.global.util.CookieNames;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Controller Test는 통합테스트로 작성
 */

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    AuthService authService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    SessionRepository sessionRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    ObjectMapper om = new ObjectMapper();

    @Test
    @DisplayName("로그인 성공 후 세션 ID를 저장한 쿠키를 담아 응답. Status = 302, Redirect = /")
    void login() throws Exception {
        // given
        Login login = Fixtures.dto().create(Login.class);
        String encodedPassword = passwordEncoder.encode(login.getPassword());
        User user = Fixtures.user().create(login.getEmail(),encodedPassword);
        userRepository.save(user);

        // expected
        mockMvc.perform(post("/login")
                        .accept(APPLICATION_JSON)
                        .contentType(MULTIPART_FORM_DATA)
                        .params(MyBooksTestUtils.toMultiValueMap(login)))
                .andExpect(status().isFound())
                .andExpect(header().string("location","/"))
                .andExpect(cookie().exists(CookieNames.SESSION_ID));
    }

    @Test
    @DisplayName("회원가입 성공시 status는 302로 응답하고, 로그인 화면으로 redirect한다")
    void signup() throws Exception {
        // given
        SignUp signUp = Fixtures.dto().create(SignUp.class);

        // expected
        mockMvc.perform(post("/signup")
                        .contentType(MULTIPART_FORM_DATA)
                        .params(MyBooksTestUtils.toMultiValueMap(signUp)))
                .andExpect(status().isFound())
                .andExpect(header().string("location", Matchers.startsWith("/login")));

        User user = userRepository.findByEmail(signUp.getEmail()).get();

        assertThat(user.getName()).isEqualTo(signUp.getName());
        assertThat(user.getEmail()).isEqualTo(signUp.getEmail());
        assertThat(user.getPassword()).isNotEqualTo(signUp.getPassword());
    }

    @Test
    @DisplayName("로그아웃시 세션 정보는 삭제되어야 한다.")
    void logout() throws Exception {
        // given
        User user = Fixtures.user().create();
        userRepository.save(user);
        Session session = authService.createSession(user.getId());

        // expected
        mockMvc.perform(post("/logout")
                        .cookie(new Cookie(CookieNames.SESSION_ID, session.getId())))
                .andExpect(status().isNoContent());

        Optional<Session> optionalSession = sessionRepository.findById(session.getId());

        assertThat(optionalSession.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("GET /login 호출시 login 페이지를 전달한다.")
    void loginForm() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(view().name("login"));
    }

    @Test
    @DisplayName("GET /singup 호출시 signup 페이지를 전달한다.")
    void signUpForm() throws Exception {
        mockMvc.perform(get("/signup"))
                .andExpect(view().name("signup"));
    }


}