package sungjin.mybooks.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import sungjin.mybooks.request.Join;
import sungjin.mybooks.request.Login;
import sungjin.mybooks.service.AuthService;
import sungjin.mybooks.service.UserService;
import sungjin.mybooks.util.CookieNames;
import sungjin.mybooks.util.CookieUtils;

import static org.junit.jupiter.api.Assertions.*;
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
        Join info = Join.builder()
                .email(email)
                .rawPassword(password)
                .name("sj")
                .build();
        userService.joinUser(info);

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
}