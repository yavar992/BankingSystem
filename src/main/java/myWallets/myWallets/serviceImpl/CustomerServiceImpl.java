package myWallets.myWallets.serviceImpl;

import lombok.extern.slf4j.Slf4j;
import myWallets.myWallets.DTO.CustomerDTO;
import myWallets.myWallets.DTO.OptDTO;
import myWallets.myWallets.entity.CurrentUserSession;
import myWallets.myWallets.entity.Customer;
import myWallets.myWallets.event.CustomerEvent;
import myWallets.myWallets.exceptionHandling.UserNotFoundException;
import myWallets.myWallets.repository.CurrentUserSessionRepo;
import myWallets.myWallets.repository.CustomerRepo;
import myWallets.myWallets.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.security.auth.login.LoginException;
import java.util.List;
import java.util.Optional;
import java.util.Spliterator;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    CustomerRepo customerRepo;

    @Autowired
    ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    CurrentUserSessionRepo currentUserSessionRepo ;

    @Override
    public Customer saveCustomer(CustomerDTO customerDTO) {
        try {
            Customer customer =Customer.builder()
                    .customerName(customerDTO.getCustomerName())
                    .address(customerDTO.getAddress())
                    .email(customerDTO.getEmail())
                    .gender(customerDTO.getGender())
                    .dateOfBirth(customerDTO.getDateOfBirth())
                    .mobileNumber(customerDTO.getMobileNumber().trim())
                    .password(customerDTO.getPassword())
                    .countryCode(customerDTO.getCountryCode())
                    .build();
              Customer customer1 = customerRepo.saveAndFlush(customer);
              log.info("User Registered Successfully !");
            CustomerEvent customerEvent = new CustomerEvent(customer1);
             applicationEventPublisher.publishEvent(customerEvent);
             return customer1;

        }catch (Exception e){
            log.error("cannot save the customer due to " + e.getMessage());
        }
        return null;
    }

    @Override
    public Customer findCustomerByOTP(String otp) {
       Optional<Customer> customer = customerRepo.findByOtp(otp);
       if(customer.isPresent()){
           return customer.get();
       }
       return null;
    }

    @Override
    public void validateCustomer(Customer customer) {
        customerRepo.saveAndFlush(customer);
    }


    @Override
    public List<Customer> getAllCustomer() {
        try {
            List<Customer> customers = customerRepo.findAll();
            if (customers!=null && !customers.isEmpty()){
                return customers;
            }
        }catch (Exception e){
            log.info("cannot get the user due to "+e.getMessage());
        }
        return null;
    }


    @Override
    public Customer getAccount(Long id) {
        Customer customer = customerRepo.findById(id).orElseThrow(()->new UserNotFoundException("User Not Found on id " + id));
        return customer;
    }

    @Override
    public Customer getCustomerByEmail(String email) {
            Customer customer = customerRepo.findByEmail(email);
            if (customer==null){
                throw new UserNotFoundException("No User Exist by Email " + email);
            }
            return customer;
    }

    @Override
    public Customer findByCustomerMobileNumber(String mobileNumber) {
        try {
            Customer customer = customerRepo.findByMobileNumber(mobileNumber);
            if (customer==null){
                throw new UserNotFoundException("User not found ");
            }
            return customer;
        }catch (Exception e){
            log.info("user not found due to " + e.getMessage());
        }
        return null;
    }

    @Override
    public Customer findByUserCurrentSession(String uuid) throws myWallets.myWallets.exceptionHandling.LoginException {
        try {
            Optional<CurrentUserSession> currentUserSession = currentUserSessionRepo.findByUUID(uuid);
            if (!currentUserSession.isPresent()){
                throw new LoginException("No User LoggedIn");
            }
            Optional<Customer> customer = customerRepo.findByUUID(uuid);
            if (!customer.isPresent()){
                throw new UserNotFoundException("No User Found");
            }
            Customer customer1 = customer.get();
            return customer1;
        }catch (LoginException e){
            throw new myWallets.myWallets.exceptionHandling.LoginException("No User LoggedIn");
        }
        catch (UserNotFoundException e){
            throw new UserNotFoundException("user not found ");
        }
        catch (Exception e){
            log.info("cannot get the user by the uuid due to " + e.getMessage());
        }
        return null;
    }

    @Override
    public CustomerDTO getUserprofile(String uuid) throws LoginException {

        //TO DO -- APPROVED ONLY USER WHO ARE AUTHENTICATE WITH OTP

        Optional<Customer> customer = customerRepo.findByUUID(uuid);
        if (!customer.isPresent()){
            throw new UserNotFoundException(" User Not Exist  ");
        }
            Optional<CurrentUserSession> currentUserSession = currentUserSessionRepo.findByUUID(uuid);
            if (!currentUserSession.isPresent()){
                throw new LoginException("No User LoggedIn");
            }
            Customer customer1 = customer.get();
            CustomerDTO customerDTO = CustomerDTO.builder()
                    .id(customer1.getId())
                    .customerName(customer1.getCustomerName())
                    .address(customer1.getAddress())
                    .mobileNumber(customer1.getMobileNumber())
                    .gender(customer1.getGender())
                    .email(customer1.getEmail())
                    .dateOfBirth(customer1.getDateOfBirth())
                    .build();
            return customerDTO;
        }


}

