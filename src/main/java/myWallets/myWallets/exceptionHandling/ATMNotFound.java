package myWallets.myWallets.exceptionHandling;

public class ATMNotFound extends RuntimeException{
    public ATMNotFound() {
        super();
    }

    public ATMNotFound(String message) {
        super(message);
    }

    public ATMNotFound(String message, Throwable cause) {
        super(message, cause);
    }
}
