package myWallets.myWallets.constant;

import myWallets.myWallets.DTO.CustomerAccountRecieveDTO;
import myWallets.myWallets.entity.BankAccount;
import myWallets.myWallets.entity.CurrentUserSession;
import myWallets.myWallets.entity.Customer;
import myWallets.myWallets.exceptionHandling.BankNotFoundException;
import myWallets.myWallets.exceptionHandling.LoginException;
import myWallets.myWallets.exceptionHandling.UnverifiedCustomerException;
import myWallets.myWallets.exceptionHandling.UserNotFoundException;
import myWallets.myWallets.repository.BankAccountRepo;
import myWallets.myWallets.repository.CurrentUserSessionRepo;
import myWallets.myWallets.repository.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class HappyBankUtilMethods {

    private final CustomerRepo customerRepo;

    @Autowired
    CurrentUserSessionRepo currentUserSessionRepo;

    @Autowired
    BankAccountRepo bankAccountRepo;
    public HappyBankUtilMethods(CustomerRepo customerRepo) {
        this.customerRepo = customerRepo;
    }

    public  Customer authorizeAndGetVerifiedCustomer(String uuid) {
        Optional<Customer> customerOptional = customerRepo.findByUUID(uuid);
        if ( customerOptional==null || customerOptional.isEmpty()) {
            throw new UserNotFoundException("No User logged in");
        }

        Customer customer = customerOptional.get();
        return customer;
    }

    public void validateCustomerSessoion(String uuid){
        Optional<CurrentUserSession> currentUserSession = currentUserSessionRepo.findByUUID(uuid);
        if (currentUserSession.isPresent()){
            throw new LoginException("user is already login");
        }
    }

    public void validateHappyBank(List<BankAccount>bankAccounts){
         bankAccounts = bankAccountRepo.findAll();
        if (bankAccounts==null || bankAccounts.isEmpty()){
            throw new BankNotFoundException("Bank account not found");
        }
    }

    public  Customer updateCustomerAccount( Long id , CustomerAccountRecieveDTO customerAccountRecieveDTO){
        Customer customer = customerRepo.findById(id).orElseThrow(()->new UserNotFoundException("Customer not found for id " + id));
        customer.setCustomerName(customerAccountRecieveDTO.getCustomerName());
        customer.setMobileNumber(customerAccountRecieveDTO.getMobileNumber());
        customer.setEmail(customerAccountRecieveDTO.getEmail());
//        customer.setDateOfBirth(customerAccountRecieveDTO.getDateOfBirth());
        customer.setUpdateTime(ZonedDateTime.now());
        return customer;
    }


}
