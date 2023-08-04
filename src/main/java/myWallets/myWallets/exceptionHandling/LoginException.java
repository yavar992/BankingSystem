package myWallets.myWallets.exceptionHandling;

public class LoginException extends  RuntimeException{
    public LoginException() {
        super();
    }

    public LoginException(String message) {
        super(message);
    }

    public LoginException(String message, Throwable cause) {
        super(message, cause);
    }
}
