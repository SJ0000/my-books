package sungjin.mybooks.domain;


import jakarta.persistence.*;
import lombok.Builder;

@Entity
@Builder
public class Review {

    protected Review() {
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
