package myWallets.myWallets.util;

import lombok.extern.slf4j.Slf4j;
import myWallets.myWallets.DTO.BankAccountDTO;
import myWallets.myWallets.constant.StatusCode;
import myWallets.myWallets.entity.*;
import myWallets.myWallets.exceptionHandling.BankBranchesNotFoundException;
import myWallets.myWallets.exceptionHandling.LoginException;
import myWallets.myWallets.exceptionHandling.UnverifiedCustomerException;
import myWallets.myWallets.repository.BankAccountRepo;
import myWallets.myWallets.repository.BankBranchRepo;
import myWallets.myWallets.repository.CurrentUserSessionRepo;
import myWallets.myWallets.repository.CustomerRepo;
import myWallets.myWallets.service.BankAccountService;
import myWallets.myWallets.service.BankBranchService;
import myWallets.myWallets.validator.Validator;
import myWallets.myWallets.validator.ValidatorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class BankAccountUtilImpl implements BankAccountUtil{




   private final CurrentUserSessionRepo currentUserSessionRepo;


  private final   CustomerRepo customerRepo;



    private final BankBranchRepo bankBranchRepo;


    private final BankAccountRepo bankAccountRepo;

    public BankAccountUtilImpl(CurrentUserSessionRepo currentUserSessionRepo, CustomerRepo customerRepo, BankBranchRepo bankBranchRepo, BankAccountRepo bankAccountRepo) {
        this.currentUserSessionRepo = currentUserSessionRepo;
        this.customerRepo = customerRepo;
        this.bankBranchRepo = bankBranchRepo;
        this.bankAccountRepo = bankAccountRepo;
    }

    @Override
    public CustomerAccountDetails openAccount(String uuid, BankAccountDTO bankAccountDTO) {

        try {
            Optional<Customer> customer = customerRepo.findByUUID(uuid);
            Customer customer1 = customer.get();
            log.info("customer 1" + customer1);
            ValidatorUtils.validateUnverifiedCustomer(customer1);
//            ValidatorUtils.validateLoggedInCustomer(customer , uuid);
            Optional<CurrentUserSession> currentUserSession = currentUserSessionRepo.findByUUID(uuid);
            log.info("currentUserSession: " + currentUserSession);
            ValidatorUtils.validateCurrentUserSession(currentUserSession,uuid);
            List<BankAccount> bankAccounts = bankAccountRepo.findAll();
            BankAccount bankAccount = bankAccounts.get(0);
            log.info("bankAccount " + bankAccount);
            Optional<BankBranches> bankBranches = bankBranchRepo.findById( bankAccountDTO.getBranchId());
            ValidatorUtils.validateBankBranch(bankBranches);
            BankBranches bankBranches1 = bankBranches.get();
            log.info("bankBranches " + bankAccounts);
            CustomerAccountDetails customerAccountDetails = CustomerAccountDetails.builder()
                    .accountOpeningDate(ZonedDateTime.now())
                    .accountType(bankAccountDTO.getAccountType().toUpperCase())
                    .accountHolderName(bankAccountDTO.getAccountHolderName().toUpperCase())
                    .accountNo("HYBK0"+bankBranches1.getBranchCode()+ Validator.accountLast4digits())
                    .bankAccount(bankAccount)
                    .balance(bankAccountDTO.getBalance())
                    .customer(customer1)
                    .currency("INR")
                    .accountCloseDate(null)
                    .Status("ACTIVE")
                    .bankBranches(bankBranches1)
                    .build();
            return customerAccountDetails;
        }catch (Exception e){
            log.error("An error occurred while opening an account: " + e.getMessage());
            throw e;
        }
    }
}
