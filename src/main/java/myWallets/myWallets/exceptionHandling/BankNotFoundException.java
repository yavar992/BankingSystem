package myWallets.myWallets.exceptionHandling;

public class BankNotFoundException extends RuntimeException{
    public BankNotFoundException() {
        super();
    }

    public BankNotFoundException(String message) {
        super(message);
    }

    public BankNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
