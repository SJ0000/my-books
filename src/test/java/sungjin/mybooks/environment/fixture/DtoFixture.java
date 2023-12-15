package sungjin.mybooks.environment.fixture;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.BuilderArbitraryIntrospector;
import com.navercorp.fixturemonkey.api.introspector.ConstructorPropertiesArbitraryIntrospector;
import com.navercorp.fixturemonkey.api.introspector.FieldReflectionArbitraryIntrospector;
import com.navercorp.fixturemonkey.jakarta.validation.plugin.JakartaValidationPlugin;
import net.jqwik.api.Arbitraries;
import org.junit.jupiter.api.Test;
import sungjin.mybooks.domain.user.dto.Login;
import sungjin.mybooks.domain.user.dto.SignUp;

public class DtoFixture {

    private final FixtureMonkey fixtureMonkey;

    public DtoFixture() {
        this.fixtureMonkey = FixtureMonkey.builder()
                .objectIntrospector(BuilderArbitraryIntrospector.INSTANCE)
                .plugin(new JakartaValidationPlugin())
                .build();
    }

    public <T> T create(Class<T> type){
        return fixtureMonkey.giveMeOne(type);
    }

}
