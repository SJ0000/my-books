package sungjin.mybooks.global.exception;

public class Unauthorized extends MyBooksException{

    private static final String MESSAGE = "인가되지 않은 접근입니다.";

    public Unauthorized() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 401;
    }
}
