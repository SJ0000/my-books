package sungjin.mybooks.domain.common.domain.security.handler;

import sungjin.mybooks.domain.user.domain.User;
// comment, review
public interface DomainAuthorizeHandler {
    void authorize(User user, Long entityId);
}
