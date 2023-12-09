package sungjin.mybooks.domain.common.domain.security.handler;

import sungjin.mybooks.domain.user.domain.User;
// comment, like, review
public interface DomainAuthorizationHandler {
    void authorize(User user, Long entityId);
}
