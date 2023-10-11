package sungjin.mybooks.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ReviewEdit {

    @NotBlank
    private String content;

    public ReviewEdit(String content) {
        this.content = content;
    }
}
