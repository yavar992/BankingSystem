package myWallets.myWallets.serviceImpl;

import lombok.extern.slf4j.Slf4j;
import myWallets.myWallets.DTO.BankAccountDTO;
import myWallets.myWallets.constant.HappyBankUtilMethods;
import myWallets.myWallets.constant.StatusCode;
import myWallets.myWallets.entity.*;
import myWallets.myWallets.event.AccountOpenEvent;
import myWallets.myWallets.exceptionHandling.BankBranchesNotFoundException;
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

    private final HappyBankUtilMethods happyBankUtilMethods;




    public CustomerAccountDetailsServiceImpl(CustomerAccountDetailsRepo customerAccountDetailsRepo,
                                             ApplicationEventPublisher applicationEventPublisher,
                                             CurrentUserSessionRepo currentUserSessionRepo,
                                             CustomerService customerService,
                                             BankBranchRepo bankBranchRepo,
                                             BankAccountRepo bankAccountRepo ,
                                             HappyBankUtilMethods happyBankUtilMethods
    ) {
        this.customerAccountDetailsRepo = customerAccountDetailsRepo;
        this.applicationEventPublisher = applicationEventPublisher;
        this.currentUserSessionRepo = currentUserSessionRepo;
        this.customerService = customerService;
        this.bankBranchRepo = bankBranchRepo;
        this.bankAccountRepo = bankAccountRepo;
        this.happyBankUtilMethods =happyBankUtilMethods;
    }

    private final BankAccountRepo bankAccountRepo;

    CustomerService customerService;

    BankBranchRepo bankBranchRepo;

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
                happyBankUtilMethods.authorizeAndGetVerifiedCustomer(uuid);
                List<BankAccount> bankAccount = bankAccountRepo.findAll();
                BankAccount bankAccount1 = bankAccount.get(0);
                BankBranches bankBranches = bankBranchRepo.findById(bankAccountDTO.getBranchId())
                        .orElseThrow(()->new BankBranchesNotFoundException("Branch Not Found"));
             log.info("bankBranches " + bankBranches);
            CustomerAccountDetails customerAccountDetails = CustomerAccountDetails.builder()
                    .accountOpeningDate(ZonedDateTime.now())
                    .bankAccountType(bankAccountDTO.getBankAccountType())
                    .accountHolderName(bankAccountDTO.getAccountHolderName().toUpperCase())
                    .accountNo("HYBK0"+bankBranches.getBranchCode()+ Validator.accountLast4digits())
//                     .bankAccount(bankAccount)
                    .balance(bankAccountDTO.getBalance())
                    // .customer(customer)
                    .currency("INR")
                    .accountCloseDate(null)
                    .Status("ACTIVE")
//                    .bankBranches(bankBranches)
                    .build();
            return customerAccountDetailsRepo.saveAndFlush(customerAccountDetails);
        }catch (Exception e){
            log.error("An error occurred while opening an account: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public void saveCustomer(CustomerAccountDetails bankAccount) {

        AccountOpenEvent accountOpenEvent = new AccountOpenEvent(bankAccount);
        applicationEventPublisher.publishEvent(accountOpenEvent);
        customerAccountDetailsRepo.saveAndFlush(bankAccount);
    }



}
