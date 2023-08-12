package myWallets.myWallets.serviceImpl;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import myWallets.myWallets.DTO.*;
import myWallets.myWallets.constant.HappyBankUtilMethods;
import myWallets.myWallets.convertor.BankAccountConvertor;
import myWallets.myWallets.convertor.BankBranchConvertor;
import myWallets.myWallets.convertor.CustomerConvertor;
import myWallets.myWallets.entity.CurrentUserSession;
import myWallets.myWallets.entity.Customer;
import myWallets.myWallets.event.CustomerEvent;
import myWallets.myWallets.exceptionHandling.InvalidOTPException;
import myWallets.myWallets.exceptionHandling.UnverifiedCustomerException;
import myWallets.myWallets.exceptionHandling.UserNotFoundException;
import myWallets.myWallets.repository.CurrentUserSessionRepo;
import myWallets.myWallets.repository.CustomerRepo;
import myWallets.myWallets.service.CustomerService;
import myWallets.myWallets.util.BankAccountUtil;
import myWallets.myWallets.validator.Validator;
import myWallets.myWallets.validator.ValidatorUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import javax.security.auth.login.LoginException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
public class CustomerServiceImpl implements CustomerService {

    CustomerRepo customerRepo;
    HappyBankUtilMethods happyBankUtilMethods;
    ApplicationEventPublisher applicationEventPublisher;
    CurrentUserSessionRepo currentUserSessionRepo ;


    public CustomerServiceImpl(CustomerRepo customerRepo,
                               HappyBankUtilMethods happyBankUtilMethods,
                               ApplicationEventPublisher applicationEventPublisher,
                               CurrentUserSessionRepo currentUserSessionRepo
                                        ) {
        this.customerRepo = customerRepo;
        this.happyBankUtilMethods = happyBankUtilMethods;
        this.applicationEventPublisher = applicationEventPublisher;
        this.currentUserSessionRepo = currentUserSessionRepo;
    }

    @Override
    public Customer saveCustomer(CustomerDTO customerDTO) {
        try {
            Customer customer = CustomerConvertor.customerDtoToCustomer(customerDTO);
              log.info("User Registered Successfully !");
            CustomerEvent customerEvent = new CustomerEvent(customer);
             applicationEventPublisher.publishEvent(customerEvent);
             return customerRepo.saveAndFlush(customer);
        }catch (Exception e){
            log.error("cannot save the customer due to " + e.getMessage());
        }
        return null;
    }

    @Override
    public String findCustomerByOTP(String otp) {
        try {
            if (otp==null || otp.isEmpty()){
                throw new InvalidOTPException("Plz enter otp");
            }
            Validator.verifyOTP(otp);
            Optional<Customer> customer = customerRepo.findByOtp(otp);
            if (customer==null || !customer.isPresent()){
                throw new myWallets.myWallets.exceptionHandling.LoginException("Invalid OTP ");
            }
            Customer customer1 = customer.get();
            String OTP = customer1.getOtp();
            if(OTP.equals(otp)){
                customer1.setOtp(null);
                customer1.setVerified(true);
             customerRepo.saveAndFlush(customer1);
             return "customer verified successfully";
            }
        }catch (Exception e){
            throw e;
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

            Optional<Customer> customer = customerRepo.findByUUID(uuid);
            log.info("customer " + customer);
            ValidatorUtils.validateLoggedInCustomer(customer,uuid);
            Customer customer1 = customer.get();
            return customer1;
        }
        catch (Exception e){
            log.info("cannot get the user by the uuid due to " + e.getMessage());
            throw e;
        }
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
                    .dateOfBirth(LocalDate.parse(String.valueOf(customer1.getDateOfBirth())))
                    .build();
            return customerDTO;
        }

    @Override
    public boolean checkIfCustomerMobileNumberOrEmailExist(String mobileNumber, String email) {
        try {
            Customer customer = customerRepo.findByMobileNumberOrEmail(mobileNumber ,email);
            if (customer!=null){
                throw new UserNotFoundException("User is already exists ");
            }
            return customer!=null;
        }catch (Exception e){
            throw e;
        }
    }

    @Override
    public Boolean checkIfaUserIsVerifiedOrNot(String uuid) {
        try {
            Optional<Customer> customer = customerRepo.findByUUID(uuid);
            Customer customer1 = customer.get();
            if (customer1!=null && customer1.isVerified()==false){
                throw new UnverifiedCustomerException("you account is not verified yet plz verify yourself ");
            }
            if (!customer.isPresent()){
                throw new myWallets.myWallets.exceptionHandling.LoginException("No User Login for uuid " + uuid);
            }
            Optional<CurrentUserSession> currentUserSession = currentUserSessionRepo.findByUUID(uuid);
            if (currentUserSession.isPresent()){
                throw new myWallets.myWallets.exceptionHandling.LoginException("User is already logged in");
            }
            if (customer1!=null && customer1.isVerified()==true){
                return true;
            }
        }catch (Exception e){
            throw e;
        }
        return false;
    }

    @Override
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public List<CustomerAccountRecieveDTO> findAllCustomer(String uuid) {
        try {
            Optional<Customer> customer = customerRepo.findByUUID(uuid);
            Customer customer1 = customer.get();
            if (customer1!=null && !customer1.isVerified()){
                throw new UnverifiedCustomerException("you are not authorized to see the list of all user ");
            }
            if (customer==null){
                throw new UserNotFoundException("No User logged in");
            }
            List<Customer> customers = customerRepo.findAll();
            return customers.stream().map(CustomerConvertor::customerToCustomerDTO).collect(Collectors.toList());

        }catch (Exception e){
            throw e;
        }
    }

    @Override
    public CustomerAccountRecieveDTO findByCustomerMobileNumber(String mobileNumber, String uuid) {
        try {
            happyBankUtilMethods.authorizeAndGetVerifiedCustomer(uuid);
            Customer customer = customerRepo.findByMobileNumber(mobileNumber);
            if (customer==null){
                throw new UserNotFoundException("User not found ");
            }
            return CustomerConvertor.customerToCustomerDTO(customer);
        }catch (Exception e){
            log.info("user not found due to " + e.getMessage());
            throw e;
        }
    }

    @Override
    public String updateCustomerAccount(Long id, String uuid, CustomerAccountRecieveDTO customerAccountRecieveDTO) {
        try {
            happyBankUtilMethods.authorizeAndGetVerifiedCustomer(uuid);
            Customer customer = happyBankUtilMethods.updateCustomerAccount(id ,customerAccountRecieveDTO);
            customerRepo.saveAndFlush(customer);
            return "Customer updated successfully !";
        }catch (Exception e){
            throw e;
        }
    }

    @Override
    public String deleteCustomer(String uuid, Long id) {
        happyBankUtilMethods.authorizeAndGetVerifiedCustomer(uuid);
        Customer customer = getAccount(id);
        customerRepo.delete(customer);
        Optional<CurrentUserSession> currentUserSession = currentUserSessionRepo.findByUUID(uuid);
        if (currentUserSession.isPresent()){
            CurrentUserSession currentUserSession1 = currentUserSession.get();
            currentUserSessionRepo.delete(currentUserSession1);
        }
        return "customer deleted successfully";

    }

    @Override
    public CustomerAccountRecieveDTO findCustomerById(String uuid, Long id) {
        Customer customer = customerRepo.findById(id).orElseThrow(()->new UserNotFoundException("Customer not found for id" + id));
        return CustomerConvertor.customerToCustomerDTO(customer);
    }

    @Override
    public CustomerAllDetails findAllCustomerDetailsByCustomerId(Long customerId, String uuid) {
        try {
            happyBankUtilMethods.authorizeAndGetVerifiedCustomer(uuid);
            Object[] customerAllDetails = customerRepo.findbyCustomerId(customerId);
            if (customerAllDetails==null || customerAllDetails.length==0){
                throw new UserNotFoundException("No customer found for the accountId " + customerId);
            }
            CustomerAccountDetailsDTO customerAccountDetailsDTO = BankAccountConvertor.mappedToCustomerAccountDetails(customerAllDetails);
            BankBranchSendarDTO bankBranchSendarDTO = BankBranchConvertor.mappedToBankBranch(customerAllDetails);
            List<CustomerAccountRecieveDTO> customerAccountRecieveDTOS = CustomerConvertor.mappedToCustomerAccountRecieve(customerAllDetails);
            CustomerAllDetails customerAllDetails1 =new CustomerAllDetails(customerAccountDetailsDTO , customerAccountRecieveDTOS ,bankBranchSendarDTO);
            return customerAllDetails1;
        }catch (Exception e){
            throw e;
        }
    }


}

