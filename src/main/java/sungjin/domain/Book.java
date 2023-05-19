package sungjin.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Book {

    @Id
    private Long id;
    private String isbn;
    private String title;
    private String thumbnail;
    private String author;

}
