package sungjin.mybooks.exception;

public class NotFound extends MyBooksException{


    public NotFound(Class<?> clazz, String findBy, Object value) {
        super(String.format("%s not found. %s = %s", clazz.getSimpleName(), findBy, value.toString()));
    }

    @Override
    public int getStatusCode() {
        return 404;
    }
}
