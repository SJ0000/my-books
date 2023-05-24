package sungjin.mybooks.domain;


import jakarta.persistence.*;

@Entity
public class Review {

    @Id
    private Long id;

    @OneToOne
    private User user;

    @ManyToOne
    private Book book;

    @Lob
    private String content;

}
