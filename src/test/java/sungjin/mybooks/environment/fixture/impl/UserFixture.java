package sungjin.mybooks.environment.fixture.impl;

import com.navercorp.fixturemonkey.ArbitraryBuilder;
import sungjin.mybooks.domain.user.domain.User;
import sungjin.mybooks.environment.fixture.AbstractFixture;

import static net.jqwik.api.Arbitraries.strings;


public class UserFixture extends AbstractFixture {

    private UserFixture() {
    }

    static {
        instance = new UserFixture();
    }

    public User createUser(String password) {
        return getUserBuilder()
                .set("password", password)
                .sample();
    }

    public User createUser() {
        return getUserBuilder()
                .sample();
    }

    private ArbitraryBuilder<User> getUserBuilder() {
        return fixtureMonkey.giveMeBuilder(User.class)
                .setNull("id")
                .set("email", strings().ofMaxLength(255))
                .set("name", strings().ofMaxLength(255))
                .set("name", strings().ascii().ofMaxLength(255));
    }
}
