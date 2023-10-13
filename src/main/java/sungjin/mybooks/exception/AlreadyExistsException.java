package sungjin.mybooks.exception;

public class AlreadyExistsException extends MyBooksException{

    public AlreadyExistsException(Class<?> clazz, String findBy, Object value) {
        super(String.format("%s already exists. %s = %s", clazz.getSimpleName(), findBy, value.toString()));
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}
