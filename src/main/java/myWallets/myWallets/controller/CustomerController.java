package myWallets.myWallets.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import myWallets.myWallets.DTO.CustomerDTO;
import myWallets.myWallets.DTO.OptDTO;
import myWallets.myWallets.entity.Customer;
import myWallets.myWallets.exceptionHandling.LoginException;
import myWallets.myWallets.exceptionHandling.UserNotFoundException;
import myWallets.myWallets.service.CustomerService;
import myWallets.myWallets.util.SendSMS;
import myWallets.myWallets.validator.Validator;
import org.aspectj.weaver.IClassFileProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customer")
@Slf4j
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @Autowired
    SendSMS sendSMS;

    @PostMapping({"signup","register"})
    public ResponseEntity<?> signup(@Valid @RequestBody CustomerDTO customerDTO) {

        try {
            if(customerService.checkIfCustomerMobileNumberOrEmailExist(customerDTO.getMobileNumber() ,customerDTO.getEmail())){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("customer is already exist ");
            }
            Customer customer = customerService.saveCustomer(customerDTO);
            if(customer!=null){
                return ResponseEntity.status(HttpStatus.CREATED).body("Registration Successful");
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("CANNOT REGISTERED CUSTOMER");
    }


    @PostMapping("mobileNum/{mobileNumber}")
    public ResponseEntity<?>getCustomerByMobileNo(@PathVariable("mobileNumber") String mobileNumber){
        try {
            Customer customers = customerService.findByCustomerMobileNumber(mobileNumber);
            if(customers!=null){
                log.info("customer " + customers.toString());
                return ResponseEntity.status(HttpStatus.OK).body(customers);
            }
        }catch (Exception e){
            log.info("cannot get the user account due to " +e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cannot get the Customer");
    }

    @GetMapping("/sendSMS")
    public ResponseEntity<?>sendSMS(){
        String otp = String.valueOf(Validator.otp());
        String mobileNumber = "8865007699";
         sendSMS.sendSMS(mobileNumber , otp);
         return ResponseEntity.status(HttpStatus.OK).body("Opt send successfully");
    }

    @PostMapping("/verifyAccount")
    public ResponseEntity<?> acctivateAccount(@RequestBody OptDTO optDTO){

        try {
            if(optDTO.getOtp()==null){
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Plz Enter OTP ");
            }
            Customer customer = customerService.findCustomerByOTP(optDTO.getOtp());
            if (customer==null){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Customer doesn't exist ");
            }

            if(optDTO.getOtp()!=null && optDTO.getOtp().equals(customer.getOtp())){
                customer.setOtp(null);
                customer.setVerified(true);
                customerService.validateCustomer(customer);
                log.info("welcome you have successfully registered");
                return ResponseEntity.status(HttpStatus.OK).body("Welcome you've successfully registered ");
            }
        }catch (Exception e){
            log.info("Couldn't Verify account due to the " + e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Couldn't verify your account ");
    }


    @GetMapping
    public ResponseEntity<?> getAllAccount(){
        try {
        List<Customer> customers = customerService.getAllCustomer();
        if (customers!=null && !customers.isEmpty()){
            return ResponseEntity.status(HttpStatus.OK).body(customers);
        }
        }catch (Exception e){
            log.info("cannot get the all user due to" +e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cannot get the User");
    }

    @GetMapping("/UserId/{id}")
    public ResponseEntity<?> getAccountById(@PathVariable("id") Long id){
        Customer customer = customerService.getAccount(id);
        if (customer!=null){
            return ResponseEntity.status(HttpStatus.OK).body(customer);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("CANNOT GET THE USER ACCOUNT");
    }

    //FIND BY EMAIL
    @GetMapping("/{email}")
    public ResponseEntity<?> getAccByEmail(@PathVariable("email") String email){
        try {
            Customer customer = customerService.getCustomerByEmail(email);
            return ResponseEntity.status(HttpStatus.OK).body(customer);
        }
        catch (UserNotFoundException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        catch (Exception e){
            log.info("cannot get the user due to " +e.getMessage());
        }
        return null;
    }


    //VIEW PROFILE
    @GetMapping("/viewProfile")
    public ResponseEntity<?> viewProfile(@RequestParam("UUID") String UUID){
        try {
            CustomerDTO customer = customerService.getUserprofile(UUID);
            if (customer!=null){
                return ResponseEntity.status(HttpStatus.OK).body(customer);
            }
        }
        catch (UserNotFoundException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
        catch (LoginException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
        catch (Exception e){
            log.info("cannot get the user profile due to "+ e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("cannot get the user profile");
    }

    //CHECK IF A CUSTOMER IS VERIFIED OR NOT
    @GetMapping("/isVerified")
    public ResponseEntity<?> checkIfaCustoemrIsVerifiedOrNot(@RequestParam("UUID") String UUID){
        try {
            Boolean customer = customerService.checkIfaUserIsVerifiedOrNot(UUID);
            if (customer!=null){
                return ResponseEntity.status(HttpStatus.OK).body("CUSTOMER IS VERIFIED");
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request");
    }

}
