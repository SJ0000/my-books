package sungjin.mybooks.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sungjin.mybooks.domain.Book;
import sungjin.mybooks.repository.BookRepository;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public Book findBook(Long id){
        return bookRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("book find error. id = " + id));
    }


}
