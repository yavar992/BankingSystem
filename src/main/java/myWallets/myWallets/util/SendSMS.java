package myWallets.myWallets.util;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SendSMS {

    @Value("${TWILIO.ACCOUNT_SID}")
    String ACCOUNT_SID;

    @Value("${TWILIO.AUTH_TOKEN}")
    String AUTH_TOKEN;

    @Value("${TWILIO.TRAIL_NUMBER}")
    String PHONE_NUMBER;


    @PostConstruct
    private void sms(){
        log.info("ACCOUNT SID " + ACCOUNT_SID);
        log.info("AUTH_TOKEN"+AUTH_TOKEN);
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }



    public String sendSMS(String mobileNo , String smsMsg){
        Message message = Message.creator(
                new PhoneNumber(mobileNo),
                new PhoneNumber(PHONE_NUMBER),
                smsMsg).create();
        return message.toString();
    }

}
