package sungjin.mybooks.environment.fixture;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.FieldReflectionArbitraryIntrospector;
import com.navercorp.fixturemonkey.jakarta.validation.plugin.JakartaValidationPlugin;
import sungjin.mybooks.environment.fixture.impl.BookFixture;
import sungjin.mybooks.environment.fixture.impl.CommentFixture;
import sungjin.mybooks.environment.fixture.impl.ReviewFixture;
import sungjin.mybooks.environment.fixture.impl.UserFixture;

public class Fixtures {

    public static final FixtureMonkey FIXTURE_MONKEY;

    static {
        FIXTURE_MONKEY = FixtureMonkey.builder()
                .objectIntrospector(FieldReflectionArbitraryIntrospector.INSTANCE)
                .plugin(new JakartaValidationPlugin())
                .build();
    }

    public static UserFixture user(){
        return (UserFixture) UserFixture.getInstance();
    }

    public static BookFixture book(){
        return (BookFixture) BookFixture.getInstance();
    }

    public static ReviewFixture review(){
        return (ReviewFixture) ReviewFixture.getInstance();
    }

    public static CommentFixture comment(){
        return (CommentFixture) CommentFixture.getInstance();
    }
}
