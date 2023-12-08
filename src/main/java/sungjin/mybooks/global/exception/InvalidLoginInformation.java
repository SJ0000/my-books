package sungjin.mybooks.global.exception;

public class InvalidLoginInformation extends MyBooksException{

    private static final String MESSAGE = "이메일/비밀번호가 일치하지 않습니다.";

    public InvalidLoginInformation() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}
