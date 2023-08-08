package myWallets.myWallets.serviceImpl;

import lombok.extern.slf4j.Slf4j;
import myWallets.myWallets.DTO.BankAccountDTO;
import myWallets.myWallets.constant.StatusCode;
import myWallets.myWallets.entity.*;
import myWallets.myWallets.exceptionHandling.LoginException;
import myWallets.myWallets.exceptionHandling.UnverifiedCustomerException;
import myWallets.myWallets.repository.*;
import myWallets.myWallets.service.BankAccountService;
import myWallets.myWallets.service.BankBranchService;
import myWallets.myWallets.service.CustomerAccountDetailsService;
import myWallets.myWallets.service.CustomerService;
import myWallets.myWallets.validator.Validator;
import myWallets.myWallets.validator.ValidatorUtils;
import org.apache.catalina.LifecycleState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CustomerAccountDetailsServiceImpl implements CustomerAccountDetailsService {


    private final CustomerAccountDetailsRepo customerAccountDetailsRepo;

    private final ApplicationEventPublisher applicationEventPublisher;

    private final CurrentUserSessionRepo currentUserSessionRepo;



    public CustomerAccountDetailsServiceImpl(CustomerAccountDetailsRepo customerAccountDetailsRepo, ApplicationEventPublisher applicationEventPublisher, CurrentUserSessionRepo currentUserSessionRepo, CustomerService customerService, BankBranchService bankBranchService, BankAccountService bankAccountService) {
        this.customerAccountDetailsRepo = customerAccountDetailsRepo;
        this.applicationEventPublisher = applicationEventPublisher;
        this.currentUserSessionRepo = currentUserSessionRepo;
        this.customerService = customerService;
        this.bankBranchService = bankBranchService;
        this.bankAccountService = bankAccountService;
    }

    private final BankAccountService bankAccountService;

    CustomerService customerService;

    BankBranchService bankBranchService;

    @Override
    public CustomerAccountDetails openAccountToBank(CustomerAccountDetails bankAccount) {
        try {
            CustomerAccountDetails customerAccountDetails = customerAccountDetailsRepo.saveAndFlush(bankAccount);
            log.info("you have successfully open a account into our bank " + customerAccountDetails);
            return customerAccountDetails;
        }catch (Exception e){
            throw e;
        }
    }

    @Override
    public CustomerAccountDetails openAccount(String uuid, BankAccountDTO bankAccountDTO) {
        try {
            Customer customer = customerService.findByUserCurrentSession(uuid);
            log.info("customer 1" + customer);
            ValidatorUtils.validateUnverifiedCustomer(customer);
            // BankAccount bankAccount = bankAccountService.findAccountById(bankAccountDTO.getBankId());
            // log.info("bankAccount " + bankAccount);
            BankBranches bankBranches = bankBranchService.bankBranchesById(bankAccountDTO.getBranchId());
            // log.info("bankBranches " + bankBranches);
            CustomerAccountDetails customerAccountDetails = CustomerAccountDetails.builder()
                    .accountOpeningDate(ZonedDateTime.now())
                    .accountType(bankAccountDTO.getAccountType().toUpperCase())
                    .accountHolderName(bankAccountDTO.getAccountHolderName().toUpperCase())
                    .accountNo("HYBK0"+bankBranches.getBranchCode()+ Validator.accountLast4digits())
                    // .bankAccount(bankAccount)
                    .balance(bankAccountDTO.getBalance())
                    .customer(customer)
                    .currency("INR")
                    .accountCloseDate(null)
                    .Status("ACTIVE")
                    .bankBranches(bankBranches)
                    .build();
            return customerAccountDetailsRepo.saveAndFlush(customerAccountDetails);
        }catch (Exception e){
            log.error("An error occurred while opening an account: " + e.getMessage());
            throw e;
        }
    }





//  AccountOpenEvent accountOpenEvent = new AccountOpenEvent(customerAccountDetails);
//            applicationEventPublisher.publishEvent(accountOpenEvent);


}
