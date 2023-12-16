package sungjin.mybooks.environment;

import jakarta.persistence.EntityManager;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import sungjin.mybooks.domain.review.domain.Comment;
import sungjin.mybooks.domain.review.domain.Like;
import sungjin.mybooks.domain.review.domain.Review;

import java.util.Arrays;


public class MyBooksTestUtils {


    public static void saveCascade(EntityManager em, Review review) {
        saveIfAbsent(em, review.getUser());
        saveIfAbsent(em, review.getBook());
        saveIfAbsent(em, review);
    }

    public static void saveCascade(EntityManager em, Comment comment) {
        saveCascade(em, comment.getReview());
        saveIfAbsent(em, comment.getUser());
        saveIfAbsent(em, comment);
    }

    public static void saveCascade(EntityManager em, Like like){
        saveCascade(em, like.getReview());
        saveIfAbsent(em, like.getUser());
        saveIfAbsent(em, like);
    }

    private static void saveIfAbsent(EntityManager em, Object o) {
        if (!em.contains(o))
            em.persist(o);
    }

    public static MultiValueMap<String, String> toMultiValueMap(Object object) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        Arrays.stream(object.getClass().getDeclaredFields())
                .forEach(field -> {
                    params.add(field.getName(), ReflectionTestUtils.getField(object, field.getName()).toString());
                });
        return params;
    }
}
