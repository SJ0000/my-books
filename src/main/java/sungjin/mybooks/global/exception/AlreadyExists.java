package sungjin.mybooks.global.exception;

public class AlreadyExists extends MyBooksException {

    public AlreadyExists(Class<?> clazz, String findBy, String value) {
        super(String.format("%s already exists. %s = %s", clazz.getSimpleName(), findBy, value));
    }

    public AlreadyExists(Class<?> clazz, String findBy, Object value) {
        this(clazz, findBy, value.toString());
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}
