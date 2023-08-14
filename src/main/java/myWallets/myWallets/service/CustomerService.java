package myWallets.myWallets.service;

import myWallets.myWallets.DTO.CustomerAccountRecieveDTO;
import myWallets.myWallets.DTO.CustomerAllDetails;
import myWallets.myWallets.DTO.CustomerDTO;
import myWallets.myWallets.entity.Customer;

import javax.security.auth.login.LoginException;
import java.util.List;
import java.util.UUID;

public interface CustomerService {
    

    Customer saveCustomer(CustomerDTO customerDTO);

    String findCustomerByOTP(String otp);

    void validateCustomer(Customer customer);


    List<Customer> getAllCustomer();


    Customer getAccount(Long id);

    Customer getCustomerByEmail(String email);


    Customer findByUserCurrentSession(String uuid) throws myWallets.myWallets.exceptionHandling.LoginException;

    CustomerDTO getUserprofile(String uuid) throws LoginException;

    boolean checkIfCustomerMobileNumberOrEmailExist(String mobileNumber, String email);

    Boolean checkIfaUserIsVerifiedOrNot(String uuid);

    List<CustomerAccountRecieveDTO> findAllCustomer(String uuid);

    CustomerAccountRecieveDTO findByCustomerMobileNumber(String mobileNumber, String uuid);

    String updateCustomerAccount(Long id, String uuid, CustomerAccountRecieveDTO customerAccountRecieveDTO);

    String deleteCustomer(String uuid, Long id);

    CustomerAccountRecieveDTO findCustomerById(String uuid, Long id);

    CustomerAllDetails findAllCustomerDetailsByCustomerId(Long customerId, String uuid);

    String deleteCustomerByUUID(String uuid);
}
