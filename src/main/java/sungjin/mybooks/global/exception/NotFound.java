package sungjin.mybooks.global.exception;

public class NotFound extends MyBooksException {


    public NotFound(Class<?> clazz, String findBy, String value) {
        super(String.format("%s not found. %s = %s", clazz.getSimpleName(), findBy, value));
    }

    public NotFound(Class<?> clazz, String findBy, Object value) {
        this(clazz, findBy, value.toString());
    }

    @Override
    public int getStatusCode() {
        return 404;
    }
}
