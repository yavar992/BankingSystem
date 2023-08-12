package myWallets.myWallets.constant;

import myWallets.myWallets.DTO.CustomerAccountRecieveDTO;
import myWallets.myWallets.entity.*;
import myWallets.myWallets.exceptionHandling.*;
import myWallets.myWallets.repository.*;
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
    BankBranchRepo bankBranchRepo;

    @Autowired
    CustomerAccountDetailsRepo customerAccountDetailsRepo;

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

    public  BankBranches validateBankBranch(String IFSCCode){
        Optional<BankBranches> bankBranches1 = bankBranchRepo.findByIFSCCode(IFSCCode);
        if (bankBranches1 == null || bankBranches1.isEmpty()){
            throw new BankBranchesNotFoundException("Bank Branch not found");
        }
        BankBranches bankBranches = bankBranches1.get();
        return bankBranches;
    }

    public CustomerAccountDetails validateCustomerAccountDetails(String accountNumber){
        CustomerAccountDetails customerAccountDetails1 = customerAccountDetailsRepo.findByAccountNumber(accountNumber);
        if (customerAccountDetails1==null){
            throw new CustomerAccountException("Could not find customer account details for account " + accountNumber);
        }
        return customerAccountDetails1;
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


    public CustomerAccountDetails validatCustomerAccountByCustomerId(Long id) {
        CustomerAccountDetails customerAccountDetails = customerAccountDetailsRepo.findById(id)
                .orElseThrow(()->new CustomerAccountException("No such customer account found for customerId" + id));
        return customerAccountDetails;
    }
}
