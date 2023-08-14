package myWallets.myWallets.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import myWallets.myWallets.DTO.CustomerAccountRecieveDTO;
import myWallets.myWallets.DTO.CustomerAllDetails;
import myWallets.myWallets.DTO.CustomerDTO;
import myWallets.myWallets.DTO.OptDTO;
import myWallets.myWallets.entity.Customer;
import myWallets.myWallets.exceptionHandling.LoginException;
import myWallets.myWallets.exceptionHandling.UserNotFoundException;
import myWallets.myWallets.service.CustomerService;
import myWallets.myWallets.util.SendSMS;
import myWallets.myWallets.validator.Validator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/customer")
@Slf4j
public class CustomerController {

    CustomerService customerService;

    SendSMS sendSMS;

    public CustomerController(CustomerService customerService, SendSMS sendSMS) {
        this.customerService = customerService;
        this.sendSMS = sendSMS;
    }


    @PostMapping({"signup","register"})
    public ResponseEntity<?> signup(@Valid @RequestBody CustomerDTO customerDTO , BindingResult bindingResult) {

        try {
            Map<String, String> validationRequest = new HashMap<>();
            if (bindingResult.hasErrors()) {
                for (FieldError fieldError : bindingResult.getFieldErrors()) {
                    validationRequest.put(fieldError.getField(), fieldError.getDefaultMessage());
                }
                return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(validationRequest.toString());
            }

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


    @PostMapping({"/mobileNum","/getUserByMobileNumber"})
    public ResponseEntity<?>getCustomerByMobileNo(@RequestParam( value = "mobileNumber") String mobileNumber , @RequestParam(value = "UUID") String UUID){
        try {
            CustomerAccountRecieveDTO customers = customerService.findByCustomerMobileNumber(mobileNumber , UUID);
            if(customers!=null){
                return ResponseEntity.status(HttpStatus.OK).body(customers);
            }
        }catch (Exception e){
            log.info("cannot get the user account due to " +e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
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
            String customer = customerService.findCustomerByOTP(optDTO.getOtp());
            if (customer!=null){
                return ResponseEntity.status(HttpStatus.OK).body("Great you have successfully authenticated ");

            }
        }catch (Exception e){
            log.info("Couldn't Verify account due to the " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Couldn't verify your account ");
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

    @GetMapping("/allCustomerInfo")
    public ResponseEntity<?> getAllCustomer(@RequestParam("UUID")String UUID){
        try {
            List<CustomerAccountRecieveDTO> customerDTOS = customerService.findAllCustomer(UUID);
            if (customerDTOS!=null && !customerDTOS.isEmpty()){
                return ResponseEntity.status(HttpStatus.OK).body(customerDTOS);
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("cannot get all customers");
    }
    
    //GET CUSTOMER BY ID
    @GetMapping("/getCustomerById/{id}")
    public ResponseEntity<?> getCustomerById(@RequestParam("UUID") String UUID , @PathVariable("id") Long id){
        try {
            CustomerAccountRecieveDTO customerAccountRecieveDTO = customerService.findCustomerById(UUID ,id);
            if(customerService!=null){
                return ResponseEntity.status(HttpStatus.OK).body(customerAccountRecieveDTO);
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.OK).body(e.getMessage());
        }
        return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body("cannot get the customer by id " + id);
    }

    //UPDATE A CUSTOMER
    @GetMapping("/updateCustomer/{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable("id") Long id ,
                                            @RequestParam("UUID") String UUID ,
                                            @RequestBody CustomerAccountRecieveDTO customerAccountRecieveDTO){
        try {
            String updateMessage = customerService.updateCustomerAccount(id ,UUID ,customerAccountRecieveDTO);
            if (updateMessage!=null){
                return ResponseEntity.status(HttpStatus.OK).body("Customer Updated Successfully");
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cannot update customer ");
    }

    //DELETE CUSTOMER
    @DeleteMapping("/deleteCustomer/{id}")
    public ResponseEntity<?> deleteCustomer(@RequestParam ("UUID") String UUID , @PathVariable("id") Long id){
        try {
            String deletedMsg = customerService.deleteCustomer(UUID ,id);
            if (deletedMsg!=null){
                return ResponseEntity.status(HttpStatus.OK).body("Customer deleted successfully");
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.OK).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cannot delete the customer account");
    }


    //GET ALL DETAILS OF CUSTOMER INCLUDING BANK BRANCH , CUSTOMER DETAILS
    public ResponseEntity<?> getAllDetailsOfCustomer(@RequestParam("UUID") String UUID , @RequestParam("customerId") Long customerId){
        try {
            CustomerAllDetails customerAllDetails = customerService.findAllCustomerDetailsByCustomerId(customerId , UUID);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.OK).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("cannot get the all details of customer ");
    }

    //DELETE CUSTOMER BY UUID
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteCustomer(@RequestParam("UUID")String UUID){
        try {
            String deletedMessage = customerService.deleteCustomerByUUID(UUID);
            if (deletedMessage!=null){
                return ResponseEntity.status(HttpStatus.OK).body("Customer deleted successfully");
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.OK).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body("Cannot delete the customer ");
    }
}
