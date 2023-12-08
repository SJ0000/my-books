package sungjin.mybooks.infra.search;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class BookSearchResult {

    private Meta meta;
    private List<Document> documents = new ArrayList<>();

    @Data
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class Meta{
        private Integer totalCount;
        private Integer pageableCount;
        private Boolean isEnd;
    }

    @Data
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class Document{
        private String title;
        private String contents;
        private String url;
        private String isbn;
        private String datetime;
        private String[] authors;
        private String publisher;
        private String[] translators;
        private Integer price;
        private Integer salePrice;
        private String thumbnail;
        private String status;

        public String getConcatenatedAuthors(){
            if(authors==null || authors.length == 0)
                return "";
            return String.join(",",authors);
        }

    }




}
