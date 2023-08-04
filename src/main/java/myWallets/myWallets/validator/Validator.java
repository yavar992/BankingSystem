package myWallets.myWallets.validator;

public interface Validator {

    static int otp() {
        int otp = (int) ((Math.random() * 900000) + 1000);
        return otp;
    }
}
