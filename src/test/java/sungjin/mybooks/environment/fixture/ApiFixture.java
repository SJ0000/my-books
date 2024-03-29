package sungjin.mybooks.environment.fixture;

import com.navercorp.fixturemonkey.FixtureMonkey;
import net.jqwik.api.Arbitraries;

import java.time.LocalDateTime;

public class ApiFixture extends MyBooksFixture{

    @Override
    protected FixtureMonkey onCreateFixtureMonkey() {
        return FixtureMonkey.builder()
                .build();
    }

    public String createString(int length){
        return Arbitraries.strings().excludeChars('\0').ofLength(length).sample();
    }

    public LocalDateTime createDateTime() {
        return fixtureMonkey.giveMeOne(LocalDateTime.class);
    }
}
