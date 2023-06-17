package sungjin.mybooks.service;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TestService {

    private final HttpSession session;

    public String id(){
        return session.getId();
    }

}
