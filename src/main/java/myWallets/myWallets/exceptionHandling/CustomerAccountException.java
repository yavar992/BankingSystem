package myWallets.myWallets.exceptionHandling;

public class CustomerAccountException extends RuntimeException{
    public CustomerAccountException() {
        super();
    }

    public CustomerAccountException(String message) {
        super(message);
    }

    public CustomerAccountException(String message, Throwable cause) {
        super(message, cause);
    }
}
