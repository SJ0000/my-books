package sungjin.mybooks.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import sungjin.mybooks.request.SignUp;
import sungjin.mybooks.request.Login;
import sungjin.mybooks.service.UserService;
import sungjin.mybooks.util.CookieNames;

import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserService userService;

    ObjectMapper om = new ObjectMapper();

    @Test
    @DisplayName("로그인 성공 후 status는 200이고 세션쿠키가 만들어져야 한다")
    void login() throws Exception {
        // given
        String email = "1234@naver.com";
        String password = "alphabet";
        SignUp info = SignUp.builder()
                .email(email)
                .password(password)
                .name("sj")
                .build();
        userService.signUpUser(info);

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
    @DisplayName("회원가입 후 비밀번호는 암호화하여")
    void join() throws Exception {
        // given
        String email = "1234@naver.com";
        String password = "alphabet";
        SignUp info = SignUp.builder()
                .email(email)
                .password(password)
                .name("sj")
                .build();
        userService.signUpUser(info);

        // expected

        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
                        .content(om.writeValueAsString(join)))
                .andExpect(status().isOk())
                .andExpect(cookie().exists(CookieNames.SESSION_ID));
    }
}