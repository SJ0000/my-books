package sungjin.mybooks.environment.fixture;

import com.navercorp.fixturemonkey.FixtureMonkey;

public abstract class MyBooksFixture {

    protected final FixtureMonkey fixtureMonkey;

    protected MyBooksFixture() {
        fixtureMonkey = createFixtureMonkey();
    }

    protected abstract FixtureMonkey createFixtureMonkey();
}
