package sungjin.mybooks.environment;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.FieldReflectionArbitraryIntrospector;
import com.navercorp.fixturemonkey.jakarta.validation.plugin.JakartaValidationPlugin;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.Arrays;

import static net.jqwik.api.Arbitraries.strings;


public class MyBooksTestUtils {

    private final static FixtureMonkey fixtureMonkey;

    //region ctor
    static {
        fixtureMonkey = FixtureMonkey.builder()
                .objectIntrospector(FieldReflectionArbitraryIntrospector.INSTANCE)
                .plugin(new JakartaValidationPlugin())
                .build();
    }
    //endregion

    public static String createRandomString(int length){
        return strings().ofLength(length).alpha().sample();
    }

    public static LocalDateTime createRandomDateTime() {
        return fixtureMonkey.giveMeOne(LocalDateTime.class);
    }

    public static String toFormData(Object object) {
        return Arrays.stream(object.getClass().getDeclaredFields())
                .map(field -> field.getName() + "=" + ReflectionTestUtils.getField(object, field.getName()))
                .reduce((a, b) -> a + "&" + b)
                .orElseGet(() -> "");
    }
}
