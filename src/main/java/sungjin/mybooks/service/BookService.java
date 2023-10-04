package sungjin.mybooks.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sungjin.mybooks.domain.Book;
import sungjin.mybooks.domain.Review;
import sungjin.mybooks.dto.response.BookResponse;
import sungjin.mybooks.dto.response.PageResponse;
import sungjin.mybooks.exception.NotFound;
import sungjin.mybooks.repository.BookRepository;
import sungjin.mybooks.repository.ReviewRepository;
import sungjin.mybooks.search.BookSearchApi;
import sungjin.mybooks.search.BookSearchResult;
import sungjin.mybooks.util.IsbnUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final ReviewRepository userBookRepository;
    private final BookSearchApi bookSearchApi;

    @Transactional(readOnly = true)
    public Book findBook(Long id){
        return bookRepository.findById(id)
                .orElseThrow(()-> new NotFound(Book.class, "id", id));
    }


    public PageResponse<BookResponse> apiSearch(String query, int page){
        BookSearchResult result = bookSearchApi.search(query, page);

        BookSearchResult.Meta meta = result.getMeta();

        List<BookResponse> data = result.getDocuments().stream()
                .map(document ->
                        BookResponse.builder()
                                .isbn(IsbnUtils.convertToISBN(document.getIsbn()))
                                .title(document.getTitle())
                                .thumbnail(document.getThumbnail())
                                .authors(document.getAuthors())
                                .publisher(document.getPublisher())
                                .build())
                .toList();

        return PageResponse.<BookResponse>builder()
                .data(data)
                .totalElements(meta.getTotalCount())
                .totalPage(meta.getPageableCount())
                .isLast(meta.getIsEnd())
                .build();
    }

}
