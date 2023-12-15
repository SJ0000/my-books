package sungjin.mybooks.environment.fixture;

import com.navercorp.fixturemonkey.ArbitraryBuilder;
import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.FieldReflectionArbitraryIntrospector;
import com.navercorp.fixturemonkey.jakarta.validation.plugin.JakartaValidationPlugin;
import net.jqwik.api.Arbitraries;

public abstract class EntityFixture<T> {

    protected final FixtureMonkey fixtureMonkey;

    protected EntityFixture() {
        fixtureMonkey = FixtureMonkey.builder()
                .objectIntrospector(FieldReflectionArbitraryIntrospector.INSTANCE)
                .build();
    }

    public abstract T create();
}
