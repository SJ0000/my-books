package sungjin.mybooks.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sungjin.mybooks.config.data.UserSession;
import sungjin.mybooks.domain.Book;
import sungjin.mybooks.dto.response.BookInfo;
import sungjin.mybooks.dto.response.PageResponse;
import sungjin.mybooks.service.BookService;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping("/books/{id}")
    public ResponseEntity<BookInfo> getBook(@PathVariable Long id){
        Book book = bookService.findBook(id);
        BookInfo bookInfo = new BookInfo(book);

        return ResponseEntity.ok()
                .body(bookInfo);
    }

    /**
     * @param page
     * Spring Data JPA와는 다르게
     * kakao api에서 1부터 시작하기 때문에 -1을 하지 않는다.
     */
    @GetMapping("/search/book")
    public ResponseEntity<PageResponse<BookInfo>> searchBook(@RequestParam String query, @RequestParam(defaultValue = "1") int page){
        PageResponse<BookInfo> result = bookService.apiSearch(query, page);
        return ResponseEntity.ok()
                .body(result);
    }

    @GetMapping("/users/books")
    public ResponseEntity<PageResponse<BookInfo>> getUserBooks(UserSession userSession, @RequestParam(defaultValue = "1") int page){
        Long userId = userSession.getUserId();
        PageResponse<BookInfo> result = bookService.searchRecentUserbooks(userId, page - 1);
        return ResponseEntity.ok()
                .body(result);
    }

    @GetMapping("/search")
    public ResponseEntity<PageResponse<BookInfo>> searchUserBook(UserSession userSession, @RequestParam String query, @RequestParam(defaultValue = "1") int page){
        Long userId = userSession.getUserId();
        PageResponse<BookInfo> result = bookService.searchUserBooks(userId, query, page - 1);
        return ResponseEntity.ok()
                .body(result);
    }
}