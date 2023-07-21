package sungjin.mybooks.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ReviewCreate {

    @NotEmpty
    private Long userBookId;

    @NotBlank
    private String content;

    @Builder
    public ReviewCreate(Long userBookId, String content) {
        this.userBookId = userBookId;
        this.content = content;
    }
}
