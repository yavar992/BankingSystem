package myWallets.myWallets.exceptionHandling;

public class InvalidAtmDetails extends RuntimeException{
    public InvalidAtmDetails() {
        super();
    }

    public InvalidAtmDetails(String message) {
        super(message);
    }

    public InvalidAtmDetails(String message, Throwable cause) {
        super(message, cause);
    }
}
