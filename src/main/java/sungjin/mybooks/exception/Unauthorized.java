package sungjin.mybooks.exception;

public class Unauthorized extends MyBooksException{

    private static final String MESSAGE = "인증되지 않은 접근입니다.";

    public Unauthorized(String message) {
        super(MESSAGE);
    }

    @Override
    int getStatusCode() {
        return 401;
    }
}
