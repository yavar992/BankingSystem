package myWallets.myWallets.exceptionHandling;

public class BankBranchesNotFoundException extends RuntimeException{
    public BankBranchesNotFoundException() {
        super();
    }

    public BankBranchesNotFoundException(String message) {
        super(message);
    }

    public BankBranchesNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
