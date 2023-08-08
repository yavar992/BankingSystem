package myWallets.myWallets.validator;

public interface Validator {

    static int otp() {
        int otp = (int) ((Math.random() * 900000) + 1000);
        return otp;
    }

    static Long accountLast4digits(){
        Long accountLast4digits = (long) ((Math.random() * 9000)+100);
        return accountLast4digits;
    }
}
