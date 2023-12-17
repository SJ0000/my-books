package sungjin.mybooks.environment.fixture;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.BuilderArbitraryIntrospector;
import com.navercorp.fixturemonkey.api.introspector.FieldReflectionArbitraryIntrospector;
import com.navercorp.fixturemonkey.jakarta.validation.plugin.JakartaValidationPlugin;

public class DtoFixture extends MyBooksFixture{

    @Override
    protected FixtureMonkey onCreateFixtureMonkey() {
        return FixtureMonkey.builder()
                .objectIntrospector(FieldReflectionArbitraryIntrospector.INSTANCE)
                .plugin(new JakartaValidationPlugin())
                .build();
    }

    public <T> T create(Class<T> type){
        return fixtureMonkey.giveMeOne(type);
    }

}
