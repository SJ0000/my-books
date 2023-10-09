package sungjin.mybooks.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.util.StringUtils;
import org.thymeleaf.util.ArrayUtils;
import sungjin.mybooks.domain.Book;

@Getter
@ToString
public class BookResponse {

    private Long id;
    private final String isbn;
    private final String title;
    private final String thumbnail;
    private final String author;
    private final String publisher;

    @Builder
    public BookResponse(Long id, String isbn, String title, String thumbnail, String author, String publisher) {
        this.id = id;
        this.isbn = isbn;
        this.title = title;
        this.thumbnail = thumbnail;
        this.author = author;
        this.publisher = publisher;
    }

    public BookResponse(Book book){
        this.id = book.getId();
        this.isbn = book.getIsbn();
        this.title = book.getTitle();
        this.thumbnail = book.getThumbnail();
        this.author = book.getAuthor();
        this.publisher = book.getPublisher();
    }
}
