package sungjin.mybooks.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import sungjin.mybooks.domain.Book;
import sungjin.mybooks.dto.response.BookResponse;
import sungjin.mybooks.dto.response.PageResponse;
import sungjin.mybooks.service.BookService;

@Controller
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    /**
     * @param page
     * Spring Data JPA와는 다르게
     * kakao api에서 1부터 시작하기 때문에 -1을 하지 않는다.
     */
    @GetMapping("/book-search")
    public String searchBook(@RequestParam(defaultValue = "") String query, @RequestParam(defaultValue = "1") int page, Model model){

        if(StringUtils.hasText(query)){
            PageResponse<BookResponse> result = bookService.apiSearch(query, page);
            model.addAttribute("books",result.getData());
            model.addAttribute("page",result.getPageInfo());
        }

        return "book-search";
    }
}