package sungjin.mybooks.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
}
