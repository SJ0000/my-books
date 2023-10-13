package sungjin.mybooks.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.swing.text.html.Option;
import java.util.Optional;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String isbn;
    private String title;
    private String thumbnail;
    private String author;
    private String publisher;

    @Builder
    public Book(String isbn, String title, String thumbnail, String author, String publisher) {
        this.isbn = isbn;
        this.title = title;
        this.thumbnail = thumbnail;
        this.author = author;
        this.publisher = publisher;
    }
}
