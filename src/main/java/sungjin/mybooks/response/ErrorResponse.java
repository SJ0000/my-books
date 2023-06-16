package sungjin.mybooks.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
public class ErrorResponse {

    private final String code;
    private final String message;

    @Builder
    public ErrorResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
