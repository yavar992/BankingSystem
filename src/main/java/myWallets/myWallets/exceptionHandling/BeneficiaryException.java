package myWallets.myWallets.exceptionHandling;

public class BeneficiaryException extends RuntimeException{
    public BeneficiaryException() {
        super();
    }

    public BeneficiaryException(String message) {
        super(message);
    }

    public BeneficiaryException(String message, Throwable cause) {
        super(message, cause);
    }
}
