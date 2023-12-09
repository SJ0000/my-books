package sungjin.mybooks.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sungjin.mybooks.domain.common.aop.DomainAuthorizeAspect;
import sungjin.mybooks.domain.common.domain.security.DomainAuthorizeManager;
import sungjin.mybooks.domain.common.domain.security.handler.CommentAuthorizeHandler;
import sungjin.mybooks.domain.common.domain.security.handler.LikeAuthorizeHandler;
import sungjin.mybooks.domain.common.domain.security.handler.ReviewAuthorizeHandler;
import sungjin.mybooks.domain.review.domain.Comment;
import sungjin.mybooks.domain.review.domain.Like;
import sungjin.mybooks.domain.review.domain.Review;
import sungjin.mybooks.domain.review.repository.CommentRepository;
import sungjin.mybooks.domain.review.repository.LikeRepository;
import sungjin.mybooks.domain.review.repository.ReviewRepository;
import sungjin.mybooks.domain.user.repository.UserRepository;

@Configuration
@RequiredArgsConstructor
public class AopConfig {

    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;
    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;

    @Bean
    DomainAuthorizeAspect domainAuthorizeAspect(){
        DomainAuthorizeManager manager = createManager();
        return new DomainAuthorizeAspect(manager);
    }

    private DomainAuthorizeManager createManager(){
        DomainAuthorizeManager manager = new DomainAuthorizeManager(userRepository);
        manager.addHandler(Review.class,new ReviewAuthorizeHandler(reviewRepository));
        manager.addHandler(Comment.class,new CommentAuthorizeHandler(commentRepository));
        manager.addHandler(Like.class,new LikeAuthorizeHandler(likeRepository));
        return manager;
    }
}
