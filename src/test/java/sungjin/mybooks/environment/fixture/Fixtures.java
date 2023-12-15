package sungjin.mybooks.environment.fixture;

import sungjin.mybooks.domain.book.domain.Book;
import sungjin.mybooks.domain.review.domain.Comment;
import sungjin.mybooks.domain.review.domain.Like;
import sungjin.mybooks.domain.review.domain.Review;
import sungjin.mybooks.domain.user.domain.User;
import sungjin.mybooks.environment.fixture.impl.*;

import java.util.HashMap;
import java.util.Map;

public class Fixtures {

    private static final Map<Class<?>, EntityFixture<?>> entityFixtures;

    private static final DtoFixture dtoFixture;

    static{
        entityFixtures = new HashMap<>();
        entityFixtures.put(User.class,new UserFixture());
        entityFixtures.put(Book.class,new BookFixture());
        entityFixtures.put(Review.class,new ReviewFixture());
        entityFixtures.put(Comment.class,new CommentFixture());
        entityFixtures.put(Like.class,new LikeFixture());
        dtoFixture = new DtoFixture();
    }

    public static UserFixture user(){
        return (UserFixture) entityFixtures.get(User.class);
    }

    public static BookFixture book(){
        return (BookFixture) entityFixtures.get(Book.class);
    }

    public static ReviewFixture review(){
        return (ReviewFixture) entityFixtures.get(Review.class);
    }

    public static CommentFixture comment(){
        return (CommentFixture) entityFixtures.get(Comment.class);
    }

    public static LikeFixture like(){
        return (LikeFixture) entityFixtures.get(Like.class);
    }

    public static DtoFixture dto(){
        return dtoFixture;
    }

}
