package myWallets.myWallets.listner;

import lombok.extern.slf4j.Slf4j;
import myWallets.myWallets.entity.ATM;
import myWallets.myWallets.entity.CustomerAccountDetails;
import myWallets.myWallets.event.ATMEvent;
import myWallets.myWallets.repository.AtmRepository;
import myWallets.myWallets.repository.CustomerAccountDetailsRepo;
import myWallets.myWallets.util.EmailSendarUtil;
import myWallets.myWallets.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.xml.transform.sax.SAXResult;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

@Service
@Slf4j
public class ATMEventListner  {

    // implements ApplicationListener<ATMEvent>

    @Autowired
    private EmailSendarUtil emailSendarUtil;
    private static final String BANK_NAME = "Happy Bank";

    @Autowired
    private AtmRepository atmRepository;

    @Async
    @EventListener
    public void onApplicationEvent(ATMEvent event) {
        try {
            ATM atm = (ATM) event.getSource();
            log.info("atm ::" + atm);
            String email = atm.getCustomerAccountDetails().getCustomer().getEmail();
            String customerAccountHolderName = atm.getCustomerAccountDetails().getCustomer().getCustomerName();
            Long otp = Validator.accountLast4digits();
            String supportPhoneNumber = atm.getCustomerAccountDetails().getBankAccount().getCustomerSupportNumber();
            String supportEmailAddress = atm.getCustomerAccountDetails().getBankAccount().getCustomerSupportEmail();

//            CustomerAccountDetails customerAccountDetails = (CustomerAccountDetails) event.getSource();
//            log.info("customerAccountDetails" + customerAccountDetails);
//            String email = customerAccountDetails.getCustomer().getEmail();
//            log.info("email " + email);
//            String customerAccountHolderName = customerAccountDetails.getCustomer().getCustomerName();
//            String supportPhoneNumber = customerAccountDetails.getBankAccount().getCustomerSupportNumber();
//            String supportEmailAddress = customerAccountDetails.getBankAccount().getCustomerSupportEmail();
            String title = "Your ATM Request: Next Steps and OTP Inside\n\n";
            emailSendarUtil.sendEmailWithMultipleBodyLine(email , Arrays.asList(

                            "Dear " +  customerAccountHolderName + ",\n\n" +
                                    "We're thrilled that you've chosen " + BANK_NAME + " to cater to your banking needs. Your request for an ATM card is on its way to becoming a reality!\n\n" +
                                    "Request Details:\n" +
                                    "Request Type: ATM Card\n" +
                                    "Request Date: " + LocalDate.now() + "\n" +
                                    "To ensure the security of your account and streamline the process, we have initiated an additional layer of protection. Below, you'll find your One-Time Password (OTP) that will unlock the door to your ATM card setup:\n\n" +
                                    "OTP: " + otp + "\n" +
                                    "Hold onto this OTP as you proceed with the following steps:\n\n" +
                                    "1. Visit your nearest " + BANK_NAME + " branch or ATM.\n" +
                                    "2. Insert your current card and input the provided OTP.\n" +
                                    "3. Follow the prompts on the screen to finalize your ATM request.\n\n" +
                                    "Your security is paramount to us, and this extra step guarantees that only you can access and control your account changes.\n\n" +
                                    BANK_NAME + " always aims to provide you with unparalleled service. If you have any queries or require assistance, our customer support team is here to help at " + supportPhoneNumber + " or " + supportEmailAddress + ".\n\n" +
                                    "Please remember: your OTP is for your eyes only. Share it with no one, and do not reply to this email directly.\n\n" +
                                    "Thank you for entrusting us with your financial journey. We look forward to serving you!\n\n" +

                                    "Warm regards,\n\n" +
                                    BANK_NAME + "\n"
                    )
                    ,title);
            atm.setAtmOtp(otp);
            atmRepository.saveAndFlush(atm);
        }catch (Exception e){
            log.info("cannot perform the action due to " +e.getMessage());
        }
    }
}
