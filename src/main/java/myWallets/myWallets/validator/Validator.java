package myWallets.myWallets.validator;

import myWallets.myWallets.exceptionHandling.InvalidOTPException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public interface Validator {

    static int otp() {
        int otp = (int) ((Math.random() * 900000) + 11111);
        return otp;
    }

    public static Integer cvv(){
        Integer cvv = (int) ((Math.random()*900)+100);
        return cvv;
    }

    public static Long atmLast10Digits(){
        Long atmLast10Digits = (long) otp()+accountLast4digits();
        return atmLast10Digits;

    }

    static Long accountLast4digits(){
        Long accountLast4digits = (long) ((Math.random() * 9000)+1000);
        return accountLast4digits;
    }

    public static void verifyOTP(String otp){
            if (otp.length()>6 || otp.length()<6){
                throw new InvalidOTPException("Invalid OTP size ");
            }
    }

    public static String generateTransactionId(){
        LocalDateTime dateTime = LocalDateTime.now(); // Replace with your LocalDateTime object
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");
        String formattedDate = "TNX"+dateTime.format(formatter) + otp();
        return formattedDate;
    }


}
