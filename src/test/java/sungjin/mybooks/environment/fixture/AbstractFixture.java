package sungjin.mybooks.environment.fixture;

import com.navercorp.fixturemonkey.FixtureMonkey;

public abstract class AbstractFixture {

    protected final FixtureMonkey fixtureMonkey = Fixtures.FIXTURE_MONKEY;
    protected static AbstractFixture instance;

    protected AbstractFixture(){}

    public static AbstractFixture getInstance() {
        if(instance == null){
            throw new RuntimeException("instance not exists");
        }
        return instance;
    }
}
