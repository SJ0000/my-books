package sungjin.mybooks.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ReviewCreate {

    @NotEmpty
    private Long bookId;

    @NotBlank
    private String content;

}
