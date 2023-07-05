package sungjin.mybooks.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public Book getBook(@PathVariable Long id){
        return bookService.findBook(id);
    }

    @GetMapping("/search/book")
    public PageResponse<BookInfo> searchBook(@RequestParam String query, @RequestParam int page){
        return bookService.apiSearch(query, page-1);
    }

    @GetMapping("/users/books")
    public PageResponse<BookInfo> getUserBooks(UserSession userSession, @RequestParam int page){
        Long userId = userSession.getUserId();
        return bookService.searchRecentUserbooks(userId, page-1);
    }

    @GetMapping("/search")
    public PageResponse<BookInfo> searchUserBook(UserSession userSession, @RequestParam String query, @RequestParam int page){
        Long userId = userSession.getUserId();
        return bookService.searchUserBooks(userId,query,page-1);
    }
}