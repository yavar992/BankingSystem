package myWallets.myWallets.validator;

import myWallets.myWallets.exceptionHandling.InvalidOTPException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public interface Validator {

    static int otp() {
        return  (int) ((Math.random() * 900000) + 11111);
    }

    public static Integer cvv(){
        return  (int) ((Math.random()*900)+100);
    }

    public static Long atmLast10Digits(){
        return  (long) otp()+accountLast4digits();


    }

    static Long accountLast4digits(){
        return  (long) ((Math.random() * 9000)+1000);

    }

    public static void verifyOTP(String otp){
            if (otp.length() != 6){
                throw new InvalidOTPException("Invalid OTP size ");
            }
    }

    public static String generateTransactionId(){
        LocalDateTime dateTime = LocalDateTime.now(); // Replace with your LocalDateTime object
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");
        return  "TNX"+dateTime.format(formatter) + otp();

    }


}
