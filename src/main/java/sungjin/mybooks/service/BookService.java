package sungjin.mybooks.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sungjin.mybooks.domain.Book;
import sungjin.mybooks.dto.response.BookResponse;
import sungjin.mybooks.dto.response.PageResponse;
import sungjin.mybooks.exception.NotFound;
import sungjin.mybooks.repository.BookRepository;
import sungjin.mybooks.search.BookSearchApi;
import sungjin.mybooks.search.BookSearchParameters;
import sungjin.mybooks.search.BookSearchResult;
import sungjin.mybooks.util.IsbnUtils;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final BookSearchApi bookSearchApi;

    @Value("${app.page-size}")
    private int pageSize;

    @Transactional(readOnly = true)
    public Book findBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new NotFound(Book.class, "id", id));
    }

    @Transactional
    public Book findOrCreateBook(String isbn) {
        Optional<Book> optionalBook = bookRepository.findByIsbn(isbn);
        if(optionalBook.isPresent())
            return optionalBook.get();

        Long createdBookId = createBook(isbn);
        return bookRepository.findById(createdBookId)
                .orElseThrow(()-> new NotFound(Book.class,"isbn", isbn));
    }

    @Transactional
    public Long createBook(String isbn){
        Book book = apiSearchByIsbn(isbn);
        bookRepository.save(book);
        return book.getId();
    }


    public PageResponse<BookResponse> apiSearch(String bookName, int page) {
        BookSearchParameters parameters = BookSearchParameters.bookName(bookName, page, pageSize);
        BookSearchResult result = bookSearchApi.request(parameters);

        BookSearchResult.Meta meta = result.getMeta();

        List<BookResponse> data = result.getDocuments().stream()
                .map(document ->
                        BookResponse.builder()
                                .isbn(IsbnUtils.convertToSingleIsbn(document.getIsbn()))
                                .title(document.getTitle())
                                .thumbnail(document.getThumbnail())
                                .author(document.getConcatenatedAuthors())
                                .publisher(document.getPublisher())
                                .build())
                .toList();

        int totalPage = (int) Math.ceil((double) meta.getTotalCount()/pageSize);

        return PageResponse.<BookResponse>builder()
                .data(data)
                .currentPage(page)
                .totalPage(totalPage)
                .build();
    }

    private Book apiSearchByIsbn(String isbn) {
        BookSearchParameters parameters = BookSearchParameters.isbn(isbn);
        BookSearchResult result = bookSearchApi.request(parameters);

        return result.getDocuments().stream()
                .map(document ->
                        Book.builder()
                                .isbn(IsbnUtils.convertToSingleIsbn(document.getIsbn()))
                                .title(document.getTitle())
                                .thumbnail(document.getThumbnail())
                                .author(document.getConcatenatedAuthors())
                                .publisher(document.getPublisher())
                                .build())
                .findAny()
                .orElseThrow(() -> new NotFound(Book.class, "isbn", isbn));
    }
}
