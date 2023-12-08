package sungjin.mybooks.global.exception;



public abstract class MyBooksException extends RuntimeException{

    public MyBooksException(String message) {
        super(message);
    }

    public abstract int getStatusCode();
}
