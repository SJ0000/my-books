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

    private final String isbn;
    private final String title;
    private final String thumbnail;
    private final String[] authors;
    private final String publisher;

    @Builder
    public BookResponse(String isbn, String title, String thumbnail, String[] authors, String publisher) {
        this.isbn = isbn;
        this.title = title;
        this.thumbnail = thumbnail;
        this.authors = authors;
        this.publisher = publisher;
    }

    public BookResponse(Book book){
        this.isbn = book.getIsbn();
        this.title = book.getTitle();
        this.thumbnail = book.getThumbnail();
        this.authors = book.getAuthors();
        this.publisher = book.getPublisher();
    }

    public String concatenateAuthors(){
        if(authors == null || authors.length == 0)
            return "";
        return String.join(",",authors);
    }


}
