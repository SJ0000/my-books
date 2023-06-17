package sungjin.mybooks.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import sungjin.mybooks.service.TestService;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final TestService testService;


    @GetMapping("/test")
    public String test(){
        return testService.id();
    }


}
