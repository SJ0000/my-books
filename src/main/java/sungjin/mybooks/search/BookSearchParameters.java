package sungjin.mybooks.search;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Optional;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BookSearchParameters {

    private String query;
    private Sort sort;
    private Integer page;
    private Integer size;
    private Target target;

    @Builder(access = AccessLevel.PRIVATE)
    private BookSearchParameters(String query, Sort sort, Integer page, Integer size, Target target) {
        this.query = query;
        this.sort = sort;
        this.page = page;
        this.size = size;
        this.target = target;
    }

    public static BookSearchParameters bookName(String name, int page, int size) {
        return BookSearchParameters.builder()
                .query(name)
                .page(page)
                .size(size)
                .target(Target.TITLE)
                .build();
    }

    public static BookSearchParameters isbn(String isbn) {
        return BookSearchParameters.builder()
                .query(isbn)
                .target(Target.ISBN)
                .build();
    }

    public MultiValueMap<String,String> createMultiValueMap(){
        MultiValueMap<String,String> map = new LinkedMultiValueMap<>();

        Optional.ofNullable(query)
                .ifPresent((query) -> map.add("query",query));
        Optional.ofNullable(sort)
                .ifPresent((sort) -> map.add("sort",sort.value));
        Optional.ofNullable(page)
                .ifPresent((page) -> map.add("page",String.valueOf(page)));
        Optional.ofNullable(size)
                .ifPresent((size) -> map.add("size",String.valueOf(size)));
        Optional.ofNullable(target)
                .ifPresent((target) -> map.add("target",target.value));

        return map;
    }

    enum Sort{
        ACCURACY("accuracy"), LATEST("latest");
        final String value;
        Sort(String value) {
            this.value = value;
        }
    }

    enum Target {
        TITLE("title"), ISBN("isbn"), PUBLISHER("publisher"), PERSON("person");
        final String value;
        Target(String value){
            this.value = value;
        }
    }
}
