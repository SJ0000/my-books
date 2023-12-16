package sungjin.mybooks.environment;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.FieldReflectionArbitraryIntrospector;
import com.navercorp.fixturemonkey.jakarta.validation.plugin.JakartaValidationPlugin;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Arrays;


public class MyBooksTestUtils {

    public static MultiValueMap<String, String> toMultiValueMap(Object object) {
        MultiValueMap<String,String> params = new LinkedMultiValueMap<>();
        Arrays.stream(object.getClass().getDeclaredFields())
                .forEach(field -> {
                    params.add(field.getName(),ReflectionTestUtils.getField(object, field.getName()).toString());
                });
        return params;
    }
}
