package sungjin.mybooks.environment.fixture.impl;

import com.navercorp.fixturemonkey.ArbitraryBuilder;
import sungjin.mybooks.domain.user.domain.User;
import sungjin.mybooks.environment.fixture.EntityFixture;

import static net.jqwik.api.Arbitraries.strings;


public class UserFixture extends EntityFixture<User> {


    @Override
    public User create() {
        return getBuilder()
                .sample();
    }

    public User create(String password) {
        return getBuilder()
                .set("password", password)
                .sample();
    }

    public User create(String email, String password) {
        return getBuilder()
                .set("email",email)
                .set("password", password)
                .sample();
    }

    private ArbitraryBuilder<User> getBuilder() {
        return fixtureMonkey.giveMeBuilder(User.class)
                .set("email", strings().ofMaxLength(255))
                .set("name", strings().ofMaxLength(255))
                .set("password", strings().ascii().ofMaxLength(255));
    }
}
