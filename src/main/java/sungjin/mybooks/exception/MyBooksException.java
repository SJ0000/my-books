package sungjin.mybooks.exception;



public abstract class MyBooksException extends RuntimeException{

    public MyBooksException(String message) {
        super(message);
    }

    abstract int getStatusCode();
}
