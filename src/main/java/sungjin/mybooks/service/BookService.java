package sungjin.mybooks.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sungjin.mybooks.domain.Book;
import sungjin.mybooks.domain.UserBook;
import sungjin.mybooks.dto.response.BookInfo;
import sungjin.mybooks.dto.response.PageResponse;
import sungjin.mybooks.exception.NotFound;
import sungjin.mybooks.repository.BookRepository;
import sungjin.mybooks.repository.UserBookRepository;
import sungjin.mybooks.search.BookSearchApi;
import sungjin.mybooks.search.BookSearchResult;
import sungjin.mybooks.util.IsbnUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final UserBookRepository userBookRepository;
    private final BookSearchApi bookSearchApi;

    @Transactional(readOnly = true)
    public Book findBook(Long id){
        return bookRepository.findById(id)
                .orElseThrow(()-> new NotFound(Book.class, "id", id));
    }

    @Transactional(readOnly = true)
    public UserBook findUserBook(Long id){
        return userBookRepository.findById(id)
                .orElseThrow(()-> new NotFound(UserBook.class, "id", id));
    }

    @Transactional
    public void deleteUserBook(Long id){
        userBookRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public PageResponse<BookInfo> searchRecentUserbooks(Long userId, int page){
        Page<UserBook> result = userBookRepository.findRecentUserBooks(userId, PageRequest.of(page, 10));
        List<BookInfo> data = result.stream()
                .map(userBook -> new BookInfo(userBook.getBook()))
                .toList();

        return PageResponse.<BookInfo>builder()
                .data(data)
                .totalPage(result.getTotalPages())
                .totalElements(result.getTotalElements())
                .isLast(result.isLast())
                .build();
    }

    @Transactional(readOnly = true)
    public PageResponse<BookInfo> searchUserBooks(Long userId, String query, int page){
        Page<UserBook> result = userBookRepository.findAllByBookTitle(userId, query, PageRequest.of(page, 10));
        List<BookInfo> data = result.stream()
                .map(userBook -> new BookInfo(userBook.getBook()))
                .toList();

        return PageResponse.<BookInfo>builder()
                .data(data)
                .totalPage(result.getTotalPages())
                .totalElements(result.getTotalElements())
                .isLast(result.isLast())
                .build();
    }


    public PageResponse<BookInfo> apiSearch(String query, int page){
        BookSearchResult result = bookSearchApi.search(query, page);

        BookSearchResult.Meta meta = result.getMeta();

        List<BookInfo> data = result.getDocuments().stream()
                .map(document ->
                        BookInfo.builder()
                                .isbn(IsbnUtils.convertToISBN(document.getIsbn()))
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
