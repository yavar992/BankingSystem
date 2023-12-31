package myWallets.myWallets.serviceImpl;

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
import myWallets.myWallets.validator.ValidatorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

@Service
@Slf4j
public class LoginServiceImpl implements LoginService {

    private final CustomerRepo customerRepo;
    private final CurrentUserSessionRepo currentUserSessionRepo;
    private final CustomerService customerService;
    private final EmailSendarUtil emailSendarUtil;

    @Autowired
    public LoginServiceImpl(CustomerRepo customerRepo, CurrentUserSessionRepo currentUserSessionRepo,
                            CustomerService customerService, EmailSendarUtil emailSendarUtil) {
        this.customerRepo = customerRepo;
        this.currentUserSessionRepo = currentUserSessionRepo;
        this.customerService = customerService;
        this.emailSendarUtil = emailSendarUtil;
    }


    @Override
    public String login(Login login) {
            Customer existingUser = customerService.getCustomerByEmail(login.getEmail());
            log.info("existing user " + existingUser);
            if (existingUser == null) {
                throw new LoginException("you are not registered plz registered yourself to login ");
            }
            if (existingUser != null && !existingUser.isVerified()) {
                throw new LoginException("Your account has not verified yet plz verifiy your account to login");
            }
            Optional<CurrentUserSession> currentUserSession = currentUserSessionRepo.findByUserId(existingUser.getId());
            log.info("current session " + currentUserSession);
            if (currentUserSession.isPresent()) {
                throw new UserAlreadyLoggedIn("User is already login ");
            }
            ValidatorUtils.validatePasswordAndEmail(login, existingUser);
            if (login.getEmail().equals(existingUser.getEmail())
                    && login.getPassword().equals(existingUser.getPassword())) {
                Long userId = existingUser.getId();
                String UUID = java.util.UUID.randomUUID().toString();
                LocalDateTime localDateTime = LocalDateTime.now();
                CurrentUserSession currentUserSession1 = new CurrentUserSession();
                currentUserSession1.setLocalDateTime(localDateTime);
                currentUserSession1.setUuid(UUID);
                currentUserSession1.setUserId(userId);
                currentUserSession1.setCustomer(existingUser);
                currentUserSessionRepo.saveAndFlush(currentUserSession1);
                existingUser.setCurrentUserSession(currentUserSession1);
                customerRepo.saveAndFlush(existingUser);
                return UUID;
            }
        return "customer login successfully ";
    }

    @Override
    public String logout(String uuid) {
        try {
            Optional<CurrentUserSession> currentUserSession = currentUserSessionRepo.findByUUID(uuid);
            if (currentUserSession.isPresent()) {
                CurrentUserSession currentUserSession1 = currentUserSession.get();
                currentUserSessionRepo.delete(currentUserSession1);
            }
            if(currentUserSession.isEmpty()){
                throw new LoginException("No user logged in");
            }
            return "you have logged out successfully";
        }catch (Exception e){
            throw e;
        }
    }

    @Override
    public String resetPassword(ForgetPasswordDTO forgetPasswordDTO) {
        Customer customer = customerRepo.findByEmail(forgetPasswordDTO.getEmail());
        if (customer == null) {
            throw new UserNotFoundException("User Not Found ");
        }
        String otp = String.valueOf(Validator.otp());
        customer.setOtp(otp);
        emailSendarUtil.sendEmailWithMultipleBodyLine(forgetPasswordDTO.getEmail(), Arrays.asList(
                "Dear " + customer.getCustomerName() + " \n\n"
                        + "Thank you for choosing Happy Bank. To ensure the security of your account, we have generated a One-Time Password (OTP) for you.\n\n"
                        + "OTP:" + otp + "\n\n"
                        + "Please use this OTP to complete your transaction or account verification. This OTP is valid for a limited time only\n\n"
                        + "For security reasons, do not share this OTP with anyone. Happy Bank will never ask for your OTP or any sensitive information over email or phone.\n\n"
                        + "If you did not request this OTP or have any concerns regarding your account's security, please contact our customer support immediately.\n\n"
                        + "Thank you for banking with us!\n\n"
                        + "Best regards,\n\n"
                        + "Happy Bank"),
                "One-Time Password (OTP) for Your Bank Account");
        customerRepo.saveAndFlush(customer);
        return "Otp Send Successfully";
    }

    @Override
    public String verifyOTP(OptDTO optDTO) {
        Optional<Customer> customer = customerRepo.findByOtp(optDTO.getOtp());
        if (customer.isEmpty()) {
            throw new UserNotFoundException("Incorrect OTP  ");
        }
        if (optDTO.getOtp()==null){
            throw new InvalidOTPException("Otp cannot be null");
        }
        Customer customer1 = customer.get();
        if (!customer1.getOtp().equals(optDTO.getOtp())) {
            throw new InvalidOTPException("Incorrect OTP");
        }
            customer1.setOtp(null);
            customer1.setVerified(true);
            customer1.setPassword(optDTO.getPassword());
        customerRepo.saveAndFlush(customer1);

        return "Password reset Successfully";
    }

}
