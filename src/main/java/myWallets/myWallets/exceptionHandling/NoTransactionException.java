package myWallets.myWallets.exceptionHandling;

public class NoTransactionException extends RuntimeException{
    public NoTransactionException() {
        super();
    }

    public NoTransactionException(String message) {
        super(message);
    }

    public NoTransactionException(String message, Throwable cause) {
        super(message, cause);
    }
}
