package sungjin.mybooks.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import sungjin.mybooks.domain.Book;

@Getter
@ToString
public class BookResponse {

    private final String isbn;
    private final String title;
    private final String thumbnail;
    private final String[] authors;

    @Builder
    public BookResponse(String isbn, String title, String thumbnail, String[] authors) {
        this.isbn = isbn;
        this.title = title;
        this.thumbnail = thumbnail;
        this.authors = authors;
    }

    public BookResponse(Book book){
        this.isbn = book.getIsbn();
        this.title = book.getTitle();
        this.thumbnail = book.getThumbnail();
        this.authors = book.getAuthors();
    }


}
