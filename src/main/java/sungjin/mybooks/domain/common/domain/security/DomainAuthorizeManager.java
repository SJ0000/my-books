package sungjin.mybooks.domain.common.domain.security;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import sungjin.mybooks.domain.common.domain.security.handler.DomainAuthorizeHandler;
import sungjin.mybooks.domain.user.domain.User;
import sungjin.mybooks.domain.user.repository.UserRepository;
import sungjin.mybooks.global.exception.NotFound;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class DomainAuthorizeManager {

    private final Map<Class<?>, DomainAuthorizeHandler> handlers = new HashMap<>();
    private final UserRepository userRepository;

    public void addHandler(Class<?> type, DomainAuthorizeHandler handler) {
        if (handlers.containsKey(type)) {
            throw new RuntimeException("type " + type.getSimpleName() + " handler already exists");
        }
        handlers.put(type, handler);
    }

    @Transactional(readOnly = true)
    public void authorize(Long userId, Class<?> type, Long entityId) {
        if (!handlers.containsKey(type)) {
            throw new RuntimeException("type " + type.getSimpleName() + " not supported");
        }

        DomainAuthorizeHandler handler = handlers.get(type);
        handler.authorize(getUser(userId), entityId);
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFound(User.class, "id", userId));
    }
}
