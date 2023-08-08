package myWallets.myWallets.exceptionHandling;

public class UnverifiedCustomerException extends RuntimeException{
    public UnverifiedCustomerException() {
        super();
    }

    public UnverifiedCustomerException(String message) {
        super(message);
    }

    public UnverifiedCustomerException(String message, Throwable cause) {
        super(message, cause);
    }
}
