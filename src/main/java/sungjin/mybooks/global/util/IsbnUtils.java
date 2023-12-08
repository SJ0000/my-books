package sungjin.mybooks.global.util;

import org.springframework.util.StringUtils;

import java.util.Arrays;

public class IsbnUtils {

    public static String convertToSingleIsbn(String isbn){
        if(!StringUtils.hasText(isbn))
            throw new RuntimeException("isbn is empty.");

        if(isbn.split(" ").length >= 2){
            return Arrays.stream(isbn.split(" "))
                    .filter(s -> s.length() == 13)
                    .findFirst()
                    .orElseThrow(()-> new RuntimeException("올바른 isbn 입력이 아닙니다."));
        }

        return isbn;
    }
}
