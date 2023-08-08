package myWallets.myWallets.service;

import myWallets.myWallets.DTO.CustomerDTO;
import myWallets.myWallets.DTO.Login;
import myWallets.myWallets.DTO.OptDTO;
import myWallets.myWallets.entity.Customer;

import javax.security.auth.login.LoginException;
import java.util.List;

public interface CustomerService {
    

    Customer saveCustomer(CustomerDTO customerDTO);

    Customer findCustomerByOTP(String otp);

    void validateCustomer(Customer customer);


    List<Customer> getAllCustomer();


    Customer getAccount(Long id);

    Customer getCustomerByEmail(String email);

    Customer findByCustomerMobileNumber(String mobileNumber);

    Customer findByUserCurrentSession(String uuid) throws myWallets.myWallets.exceptionHandling.LoginException;

    CustomerDTO getUserprofile(String uuid) throws LoginException;

    boolean checkIfCustomerMobileNumberOrEmailExist(String mobileNumber, String email);

    Boolean checkIfaUserIsVerifiedOrNot(String uuid);
}
