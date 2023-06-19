package sungjin.mybooks.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import sungjin.mybooks.domain.Book;
import sungjin.mybooks.dto.response.BookInfo;
import sungjin.mybooks.service.BookService;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping("/books/{id}")
    public Book getBook(@PathVariable Long id){
        return bookService.findBook(id);
    }

    @GetMapping("/search/books")
    public Page<BookInfo> searchBook(@RequestParam String query, @RequestParam int page){

        bookService.apiSearch(query,page);


    }

}
