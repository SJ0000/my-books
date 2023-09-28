package sungjin.mybooks.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ReviewCreate {

    @NotNull
    private Long bookId;

    @NotBlank
    private String content;

    @Builder
    public ReviewCreate(Long bookId, String content) {
        this.bookId = bookId;
        this.content = content;
    }
}
