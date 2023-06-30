package sungjin.mybooks.domain;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
public class Review extends BaseTimeEntity{

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

    public void editContent(String content){
        this.content = content;
    }

}
