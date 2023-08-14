package myWallets.myWallets.exceptionHandling;

public class CardExpirationException extends RuntimeException{
    public CardExpirationException() {
        super();
    }

    public CardExpirationException(String message) {
        super(message);
    }

    public CardExpirationException(String message, Throwable cause) {
        super(message, cause);
    }
}
