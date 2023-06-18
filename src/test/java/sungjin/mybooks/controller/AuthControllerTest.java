package sungjin.mybooks.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import sungjin.mybooks.domain.Session;
import sungjin.mybooks.domain.User;
import sungjin.mybooks.repository.SessionRepository;
import sungjin.mybooks.repository.UserRepository;
import sungjin.mybooks.request.SignUp;
import sungjin.mybooks.request.Login;
import sungjin.mybooks.service.AuthService;
import sungjin.mybooks.service.UserService;
import sungjin.mybooks.util.CookieNames;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


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


    ObjectMapper om = new ObjectMapper();

    @Test
    @DisplayName("로그인 성공 후 status는 200이고 세션쿠키가 만들어져야 한다")
    void login() throws Exception {
        // given
        String email = "1234@naver.com";
        String password = "alphabet";
        User user = User.builder()
                .name("testuser")
                .password(password)
                .email(email)
                .build();
        userRepository.save(user);

        // expected
        Login login = Login.builder()
                .email(email)
                .password(password)
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
                        .content(om.writeValueAsString(login)))
                .andExpect(status().isOk())
                .andExpect(cookie().exists(CookieNames.SESSION_ID));
    }

    @Test
    @DisplayName("회원가입 성공시 status는 201로 응답한다.")
    void signup() throws Exception {
        // given
        String email = "1234@naver.com";
        String password = "alphabet";
        String name = "sj";
        SignUp signUp = SignUp.builder()
                .email(email)
                .password(password)
                .name(name)
                .build();

        // expected
        mockMvc.perform(MockMvcRequestBuilders.post("/signup")
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
                        .content(om.writeValueAsString(signUp)))
                .andExpect(status().isCreated());

        User user = userRepository.findByEmail(email).get();

        assertThat(user.getName()).isEqualTo(name);
        assertThat(user.getEmail()).isEqualTo(email);
        assertThat(user.getPassword()).isNotEqualTo(password);
    }

    @Test
    @DisplayName("로그아웃시 세션 정보는 삭제되어야 한다.")
    void logout() throws Exception {
        // given
        User user = User.builder()
                .name("testuser")
                .password("123")
                .email("alpha@beta.com")
                .build();
        userRepository.save(user);

        Session session = authService.createSession(user);

        // expected
        mockMvc.perform(MockMvcRequestBuilders.post("/logout")
                        .accept(APPLICATION_JSON)
                        .cookie(new Cookie(CookieNames.SESSION_ID, session.getAccessToken())))
                .andExpect(status().isNoContent());

        Optional<Session> optionalSession = sessionRepository.findByAccessToken(session.getAccessToken());

        assertThat(optionalSession.isEmpty()).isTrue();

    }

}