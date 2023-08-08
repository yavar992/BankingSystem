package myWallets.myWallets.listner;

import lombok.extern.slf4j.Slf4j;
import myWallets.myWallets.DTO.CustomerDTO;
import myWallets.myWallets.DTO.OptDTO;
import myWallets.myWallets.entity.Customer;
import myWallets.myWallets.event.CustomerEvent;
import myWallets.myWallets.repository.CustomerRepo;
import myWallets.myWallets.service.CustomerService;
import myWallets.myWallets.util.EmailSendarUtil;
import myWallets.myWallets.util.SendSMS;
import myWallets.myWallets.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@Slf4j
public class CustomerEventListner  {

//implements ApplicationListener<CustomerEvent>
    @Autowired
   EmailSendarUtil emailSendarUtil;



    @Autowired
    CustomerRepo customerRepo;

    @Async
    @EventListener
    public void onApplicationEvent(CustomerEvent event) {
        try {
            Customer customer = (Customer) event.getSource();
            String customerName = customer.getCustomerName();
            String email = customer.getEmail();
            String otp =  String.valueOf(Validator.otp());
            emailSendarUtil.sendEmailWithMultipleBodyLine(email, Arrays.asList( "Dear " + customerName + " \n\n" +
                    "Thank you for choosing Happy Bank as your financial partner. We're excited to have you onboard! \n\n" +
                    "To complete the registration process and activate your account, please use the following One-Time Password (OTP):\n\n" +
                    "OTP "+ otp + "\n\n"
                    + "Please enter this OTP on the activation page to gain full access to your Happy Bank account. This step ensures the security of your account and your information.\n\n"
                    +"If you did not initiate this request or have any concerns, please contact our customer support immediately at happybank@org.in or 9020000001.\n\n"
                    +"We look forward to serving you and providing you with a seamless banking experience. Thank you for trusting Happy Bank with your financial needs.\n\n"
                    +"Best regards,\n\n"
                    +"Happy Bank Team"
            ) ,"Your Happy Bank Account Activation OTP");
            customer.setOtp(otp);
            customerRepo.saveAndFlush(customer);
        }catch (Exception e){
            log.info("cannot send the sms and cannot save otp to db due to "+e.getMessage());
        }
    }
}
