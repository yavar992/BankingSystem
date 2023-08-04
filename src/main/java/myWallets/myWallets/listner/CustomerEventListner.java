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

@Service
@Slf4j
public class CustomerEventListner  {

//implements ApplicationListener<CustomerEvent>
    @Autowired
    SendSMS sendSMS;

    @Autowired
    CustomerRepo customerRepo;

    @Async
    @EventListener
    public void onApplicationEvent(CustomerEvent event) {
        try {
            Customer customer = (Customer) event.getSource();
            String mobileNumber = "+91" + customer.getMobileNumber();
            String otp =  String.valueOf(Validator.otp());
            sendSMS.sendSMS(mobileNumber , "Your OTP for the account activation is : "+otp);
            customer.setOtp(otp);
            customerRepo.saveAndFlush(customer);
        }catch (Exception e){
            log.info("cannot send the sms and cannot save otp to db due to "+e.getMessage());
        }
    }
}
