package myWallets.myWallets.validator;

import myWallets.myWallets.exceptionHandling.InvalidOTPException;

public interface Validator {

    static int otp() {
        int otp = (int) ((Math.random() * 900000) + 11111);
        return otp;
    }

    static Long accountLast4digits(){
        Long accountLast4digits = (long) ((Math.random() * 9000)+100);
        return accountLast4digits;
    }

    public static void verifyOTP(String otp){
            if (otp.length()>6 || otp.length()<6){
                throw new InvalidOTPException("Invalid OTP size ");
            }
    }


}
