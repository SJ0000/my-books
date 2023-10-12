package sungjin.mybooks.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import sungjin.mybooks.domain.User;
import sungjin.mybooks.service.UserService;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping("/users/{id}")
    public User user(@PathVariable Long id){
        return userService.findUserByEmail(id);
    }

}
