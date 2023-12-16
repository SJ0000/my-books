package sungjin.mybooks.environment.fixture;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.FieldReflectionArbitraryIntrospector;

public abstract class EntityFixture<T> extends MyBooksFixture{

    @Override
    protected FixtureMonkey createFixtureMonkey() {
        return FixtureMonkey.builder()
                .objectIntrospector(FieldReflectionArbitraryIntrospector.INSTANCE)
                .build();
    }

    public abstract T create();
}
