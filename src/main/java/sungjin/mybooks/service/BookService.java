package sungjin.mybooks.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sungjin.mybooks.domain.Book;
import sungjin.mybooks.dto.response.BookInfo;
import sungjin.mybooks.dto.response.PageResponse;
import sungjin.mybooks.exception.NotFound;
import sungjin.mybooks.repository.BookRepository;
import sungjin.mybooks.repository.UserBookRepository;
import sungjin.mybooks.search.BookSearchApi;
import sungjin.mybooks.search.BookSearchResult;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final UserBookRepository userBookRepository;
    private final BookSearchApi bookSearchApi;

    public Book findBook(Long id){
        return bookRepository.findById(id)
                .orElseThrow(()-> new NotFound(Book.class, "id", id));
    }

    public PageResponse<BookInfo> apiSearch(String query, int page){
        BookSearchResult result = bookSearchApi.search(query, page);

        BookSearchResult.Meta meta = result.getMeta();

        List<BookInfo> data = result.getDocuments().stream()
                .map(document ->
                        BookInfo.builder()
                                .isbn(document.getIsbn())
                                .title(document.getTitle())
                                .thumbnail(document.getThumbnail())
                                .authors(document.getAuthors())
                                .build())
                .toList();

        return PageResponse.<BookInfo>builder()
                .data(data)
                .totalElements(meta.getTotalCount())
                .totalPage(meta.getPageableCount())
                .isLast(meta.getIsEnd())
                .build();
    }
}
