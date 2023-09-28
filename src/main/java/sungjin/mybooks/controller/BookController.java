package sungjin.mybooks.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sungjin.mybooks.annotation.AuthRequired;
import sungjin.mybooks.config.data.UserSession;
import sungjin.mybooks.domain.Book;
import sungjin.mybooks.dto.response.BookResponse;
import sungjin.mybooks.dto.response.PageResponse;
import sungjin.mybooks.service.BookService;

@Controller
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @AuthRequired
    @GetMapping("/books")
    public String userBooksPage(UserSession userSession, @RequestParam(defaultValue = "") String query, @RequestParam(defaultValue = "1") int page, Model model){
        Long userId = userSession.getUserId();
        PageResponse<BookResponse> result = bookService.searchUserBooks(userId, query, page);
        model.addAttribute("books", result.getData());
        model.addAttribute("page", result.getPageInfo());
        return "book-list";
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<BookResponse> getBook(@PathVariable Long id){
        Book book = bookService.findBook(id);
        BookResponse bookInfo = new BookResponse(book);

        return ResponseEntity.ok()
                .body(bookInfo);
    }

    /**
     * @param page
     * Spring Data JPA와는 다르게
     * kakao api에서 1부터 시작하기 때문에 -1을 하지 않는다.
     */
    @GetMapping("/search/book")
    public ResponseEntity<PageResponse<BookResponse>> searchBook(@RequestParam String query, @RequestParam(defaultValue = "1") int page){
        PageResponse<BookResponse> result = bookService.apiSearch(query, page);
        return ResponseEntity.ok()
                .body(result);
    }

    @GetMapping("/users/books")
    public ResponseEntity<PageResponse<BookResponse>> getUserBooks(UserSession userSession, @RequestParam(defaultValue = "1") int page){
        Long userId = userSession.getUserId();
        PageResponse<BookResponse> result = bookService.searchRecentUserbooks(userId, page - 1);
        return ResponseEntity.ok()
                .body(result);
    }

    @GetMapping("/search")
    public ResponseEntity<PageResponse<BookResponse>> searchUserBook(UserSession userSession, @RequestParam String query, @RequestParam(defaultValue = "1") int page){
        Long userId = userSession.getUserId();
        PageResponse<BookResponse> result = bookService.searchUserBooks(userId, query, page - 1);
        return ResponseEntity.ok()
                .body(result);
    }
}