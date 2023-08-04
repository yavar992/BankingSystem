package myWallets.myWallets.exceptionHandling;

public class InvalidOTPException extends RuntimeException {

    public InvalidOTPException() {
        super();
    }

    public InvalidOTPException(String message) {
        super(message);
    }

    public InvalidOTPException(String message, Throwable cause) {
        super(message, cause);
    }
}
