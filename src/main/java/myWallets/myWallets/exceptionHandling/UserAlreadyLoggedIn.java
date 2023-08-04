package myWallets.myWallets.exceptionHandling;

public class UserAlreadyLoggedIn extends  RuntimeException{
    public UserAlreadyLoggedIn() {
        super();
    }

    public UserAlreadyLoggedIn(String message) {
        super(message);
    }

    public UserAlreadyLoggedIn(String message, Throwable cause) {
        super(message, cause);
    }
}
