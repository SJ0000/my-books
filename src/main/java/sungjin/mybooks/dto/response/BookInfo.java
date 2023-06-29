package sungjin.mybooks.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import sungjin.mybooks.domain.Book;
import sungjin.mybooks.domain.UserBook;

@Getter
@Setter
@ToString
public class BookInfo {

    private String isbn;
    private String title;
    private String thumbnail;
    private String[] authors;

    @Builder
    public BookInfo(String isbn, String title, String thumbnail, String[] authors) {
        this.isbn = isbn;
        this.title = title;
        this.thumbnail = thumbnail;
        this.authors = authors;
    }

    public BookInfo(Book book){
        this.isbn = book.getIsbn();
        this.title = book.getTitle();
        this.thumbnail = book.getThumbnail();
        this.authors = book.getAuthors();
    }


}
