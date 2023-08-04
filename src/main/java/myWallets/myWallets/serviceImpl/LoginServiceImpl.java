package myWallets.myWallets.serviceImpl;

import com.sun.jdi.event.ExceptionEvent;
import lombok.extern.slf4j.Slf4j;
import myWallets.myWallets.DTO.ForgetPasswordDTO;
import myWallets.myWallets.DTO.Login;
import myWallets.myWallets.DTO.OptDTO;
import myWallets.myWallets.entity.CurrentUserSession;
import myWallets.myWallets.entity.Customer;
import myWallets.myWallets.exceptionHandling.InvalidOTPException;
import myWallets.myWallets.exceptionHandling.LoginException;
import myWallets.myWallets.exceptionHandling.UserAlreadyLoggedIn;
import myWallets.myWallets.exceptionHandling.UserNotFoundException;
import myWallets.myWallets.repository.CurrentUserSessionRepo;
import myWallets.myWallets.repository.CustomerRepo;
import myWallets.myWallets.service.CustomerService;
import myWallets.myWallets.service.LoginService;
import myWallets.myWallets.util.EmailSendarUtil;
import myWallets.myWallets.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class LoginServiceImpl implements LoginService {

    @Autowired
    CustomerRepo customerRepo;

    @Autowired
    CurrentUserSessionRepo currentUserSessionRepo;

    @Autowired
    CustomerService customerService;

    @Autowired
    EmailSendarUtil emailSendarUtil;


    @Override
    public String login(Login login) {

                try {
                    Customer existingUser = customerRepo.findByEmail(login.getEmail());
                    log.info("existing customer " + existingUser);
                    if (existingUser==null){
                        throw new UserNotFoundException("User Not Exist ");
                    }
                    Optional<CurrentUserSession> currentUserSession = currentUserSessionRepo.findByUserId(existingUser.getId());
                    log.info("current session " + currentUserSession);
                    if (currentUserSession.isPresent()){
                        throw new UserAlreadyLoggedIn("User is already login ");
                    }
                    if (login.getEmail().equals(existingUser.getEmail()) && login.getPassword().equals(existingUser.getPassword())){
                        Long userId = existingUser.getId();
                        String UUID = java.util.UUID.randomUUID().toString();
                        LocalDateTime localDateTime = LocalDateTime.now();
                        CurrentUserSession currentUserSession1 = new CurrentUserSession(userId ,UUID ,localDateTime);
                        currentUserSessionRepo.saveAndFlush(currentUserSession1);
                        return UUID;
                    }
                }catch (LoginException e){
                    throw new LoginException("Bad Credential - either password or email is incorrect ");
                }
                catch (UserAlreadyLoggedIn e){
                    throw new UserAlreadyLoggedIn("user is already loggedIn");
                }
                catch (UserNotFoundException e){
                    throw new UserNotFoundException("User Not Found ");
                }
                catch (Exception e){
                    log.error("cannot login due to " +e.getMessage());
                }
                return null;
    }

    @Override
    public String logout(String uuid)  {
        Customer customer = customerService.findByUserCurrentSession(uuid);
        if (customer!=null){
            Optional<CurrentUserSession> currentUserSession = currentUserSessionRepo.findByUUID(uuid);
            if (currentUserSession.isPresent()){
                CurrentUserSession currentUserSession1 = currentUserSession.get();
                currentUserSessionRepo.delete(currentUserSession1);
            }
        }
        return customer.getCustomerName() + " logout successfully";
    }

    @Override
    public String resetPassword(ForgetPasswordDTO forgetPasswordDTO) {
        Customer customer = customerRepo.findByEmail(forgetPasswordDTO.getEmail());
        if (customer==null){
            throw new UserNotFoundException("User Not Found ");
        }
        String otp = String.valueOf(Validator.otp());
        customer.setOtp(otp);
        emailSendarUtil.sendEmailWithMultipleBodyLine(forgetPasswordDTO.getEmail() , Arrays.asList(
                "Dear " +  customer.getCustomerName() + " \n\n"
                +"Thank you for choosing Happy Bank. To ensure the security of your account, we have generated a One-Time Password (OTP) for you.\n\n"
                + "OTP:" + otp +"\n\n"
                +"Please use this OTP to complete your transaction or account verification. This OTP is valid for a limited time only\n\n"
                +"For security reasons, do not share this OTP with anyone. Happy Bank will never ask for your OTP or any sensitive information over email or phone.\n\n"
                +"If you did not request this OTP or have any concerns regarding your account's security, please contact our customer support immediately.\n\n"
                +"Thank you for banking with us!\n\n"
                +"Best regards,\n\n"
                +"Happy Bank"
        ) ,"One-Time Password (OTP) for Your Bank Account");
        customerRepo.saveAndFlush(customer);
        return "Otp Send Successfully";
    }

    @Override
    public String verifyOTP(OptDTO optDTO) {
        Optional<Customer> customer = customerRepo.findByOtp(optDTO.getOtp());
        if (!customer.isPresent()){
            throw new UserNotFoundException("Incorrect OTP  ");
        }
        Customer customer1 = customer.get();
        if (!optDTO.getOtp().equals(customer1.getOtp().trim())){
            throw new InvalidOTPException("Incorrect OTP");
        }
        if (optDTO.getOtp()!=null && optDTO.getOtp().equals(customer1.getOtp().trim())){
            customer1.setOtp(null);
            customer1.setVerified(true);
            customer1.setPassword(optDTO.getPassword());
        }
        customerRepo.saveAndFlush(customer1);

        return "Password reset Successfully";
    }

}
