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
    public Review(UserBook userBook, String content) {
        this.userBook = userBook;
        this.content = content;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private UserBook userBook;


    @Lob
    private String content;

    public void editContent(String content){
        this.content = content;
    }

}
