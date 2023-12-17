package sungjin.mybooks.environment.fixture;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.BuilderArbitraryIntrospector;

public abstract class EntityFixture<T> extends MyBooksFixture{

    @Override
    protected FixtureMonkey onCreateFixtureMonkey() {
        return FixtureMonkey.builder()
                .objectIntrospector(BuilderArbitraryIntrospector.INSTANCE)
                .build();
    }

    public abstract T create();
}
