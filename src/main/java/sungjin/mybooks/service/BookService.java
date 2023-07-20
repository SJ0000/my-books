package sungjin.mybooks.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sungjin.mybooks.domain.Book;
import sungjin.mybooks.domain.UserBook;
import sungjin.mybooks.dto.response.BookResponse;
import sungjin.mybooks.dto.response.PageResponse;
import sungjin.mybooks.exception.NotFound;
import sungjin.mybooks.repository.BookRepository;
import sungjin.mybooks.repository.UserBookRepository;
import sungjin.mybooks.search.BookSearchApi;
import sungjin.mybooks.search.BookSearchResult;
import sungjin.mybooks.util.IsbnUtils;

import java.util.List;

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
    public PageResponse<BookResponse> searchRecentUserbooks(Long userId, int page){
        Page<UserBook> result = userBookRepository.findRecentUserBooks(userId, PageRequest.of(page, 10));
        List<BookResponse> data = result.stream()
                .map(userBook -> new BookResponse(userBook.getBook()))
                .toList();

        return PageResponse.<BookResponse>builder()
                .data(data)
                .totalPage(result.getTotalPages())
                .totalElements(result.getTotalElements())
                .isLast(result.isLast())
                .build();
    }

    @Transactional(readOnly = true)
    public PageResponse<BookResponse> searchUserBooks(Long userId, String query, int page){
        Page<UserBook> result = userBookRepository.findAllByBookTitle(userId, query, PageRequest.of(page, 10));
        List<BookResponse> data = result.stream()
                .map(userBook -> new BookResponse(userBook.getBook()))
                .toList();

        return PageResponse.<BookResponse>builder()
                .data(data)
                .totalPage(result.getTotalPages())
                .totalElements(result.getTotalElements())
                .isLast(result.isLast())
                .build();
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
                                .build())
                .toList();

        return PageResponse.<BookResponse>builder()
                .data(data)
                .totalElements(meta.getTotalCount())
                .totalPage(meta.getPageableCount())
                .isLast(meta.getIsEnd())
                .build();
    }

    @Transactional
    public boolean isUserBookOwner(Long userId, Long userBookId){
        UserBook userBook = findUserBook(userBookId);
        return userBook.isOwner(userId);
    }

}
