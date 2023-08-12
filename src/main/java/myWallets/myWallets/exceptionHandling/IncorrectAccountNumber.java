package myWallets.myWallets.exceptionHandling;

public class IncorrectAccountNumber extends RuntimeException{
    public IncorrectAccountNumber() {
        super();
    }

    public IncorrectAccountNumber(String message) {
        super(message);
    }

    public IncorrectAccountNumber(String message, Throwable cause) {
        super(message, cause);
    }
}
