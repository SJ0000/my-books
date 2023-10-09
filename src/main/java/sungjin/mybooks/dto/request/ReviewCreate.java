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

    @NotBlank
    private String content;

    @Builder
    public ReviewCreate(String content) {
        this.content = content;
    }
}
