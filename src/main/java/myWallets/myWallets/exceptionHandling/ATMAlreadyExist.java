package myWallets.myWallets.exceptionHandling;

public class ATMAlreadyExist extends RuntimeException{
    public ATMAlreadyExist() {
        super();
    }

    public ATMAlreadyExist(String message) {
        super(message);
    }

    public ATMAlreadyExist(String message, Throwable cause) {
        super(message, cause);
    }
}
