package sungjin.mybooks.domain;


import jakarta.persistence.*;
import lombok.Builder;

@Entity
public class Review {

    protected Review() {
    }

    @Builder
    public Review(User user, Book book, String content) {
        this.user = user;
        this.book = book;
        this.content = content;
    }

    @Id
    private Long id;

    @OneToOne
    private User user;

    @ManyToOne
    private Book book;

    @Lob
    private String content;

    public void writtenBy(Long writerId){

    }

}
