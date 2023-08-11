package myWallets.myWallets.util;

import lombok.extern.slf4j.Slf4j;
import myWallets.myWallets.DTO.BankAccountDTO;
import myWallets.myWallets.constant.HappyBankUtilMethods;
import myWallets.myWallets.constant.StatusCode;
import myWallets.myWallets.convertor.BankAccountConvertor;
import myWallets.myWallets.entity.*;
import myWallets.myWallets.exceptionHandling.BankBranchesNotFoundException;
import myWallets.myWallets.exceptionHandling.LoginException;
import myWallets.myWallets.exceptionHandling.UnverifiedCustomerException;
import myWallets.myWallets.repository.*;
import myWallets.myWallets.service.BankAccountService;
import myWallets.myWallets.service.BankBranchService;
import myWallets.myWallets.service.CustomerService;
import myWallets.myWallets.validator.Validator;
import myWallets.myWallets.validator.ValidatorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class BankAccountUtilImpl implements BankAccountUtil{
    private final   CurrentUserSessionRepo currentUserSessionRepo;
   private final   HappyBankUtilMethods happyBankUtilMethods;
   private final   BankAccountRepo bankAccountRepo;
  private final   CustomerService customerService;
  private final   BankBranchRepo bankBranchRepo;
   private final CustomerRepo customerRepo;

    public BankAccountUtilImpl(CurrentUserSessionRepo currentUserSessionRepo,
                               HappyBankUtilMethods happyBankUtilMethods,
                               BankAccountRepo bankAccountRepo,
                               CustomerService customerService,
                               BankBranchRepo bankBranchRepo,
                               CustomerRepo customerRepo) {
        this.currentUserSessionRepo = currentUserSessionRepo;
        this.happyBankUtilMethods = happyBankUtilMethods;
        this.bankAccountRepo = bankAccountRepo;
        this.customerService = customerService;
        this.bankBranchRepo = bankBranchRepo;
        this.customerRepo = customerRepo;
    }

    @Override
    public CustomerAccountDetails openAccount(String uuid, BankAccountDTO bankAccountDTO , Long branchId) {

        try {
            Customer customer = happyBankUtilMethods.authorizeAndGetVerifiedCustomer(uuid);
            BankAccount bankAccounts = bankAccountRepo.findBankAccountById(1L);
            log.info("bankAccount: " + bankAccounts);
            BankBranches bankBranches = bankBranchRepo.findById(branchId)
                    .orElseThrow(()->new BankBranchesNotFoundException("Branch Not Found"));
            customer.setBankBranches(bankBranches);
            customerRepo.saveAndFlush(customer);
            log.info("bankBranches " + bankBranches);
           CustomerAccountDetails customerAccountDetails = BankAccountConvertor.convertBankAccountDtoToBankAccount(bankAccountDTO);
           customerAccountDetails.setAccountNo("HYBK"+bankBranches.getBranchCode() + Validator.accountLast4digits());  //bankBranches.getBranchCode()
           customerAccountDetails.setAccountOpeningDate(ZonedDateTime.now());
           customerAccountDetails.setCurrency("INR");
           customerAccountDetails.setStatus("ACTIVE");
           customerAccountDetails.setCustomer(customer);
           customerAccountDetails.setBankAccount(bankAccounts);
            return customerAccountDetails;
        }catch (Exception e){
            log.error("An error occurred while opening an account: " + e.getMessage());
            throw e;
        }
    }
}


// CustomerAccountDetails customerAccountDetails = CustomerAccountDetails.builder()
//                    .accountOpeningDate(ZonedDateTime.now())
//                    .bankAccountType(bankAccountDTO.getBankAccountType())
//                    .accountHolderName(bankAccountDTO.getAccountHolderName().toUpperCase())
//                    .accountNo("HYBK0"+bankBranches.getBranchCode()+ Validator.accountLast4digits())
////                     .bankAccount(bankAccount)
//                    .balance(bankAccountDTO.getBalance())
//                    // .customer(customer)
//                    .currency("INR")
//                    .accountCloseDate(null)
//                    .Status("ACTIVE")
////                    .bankBranches(bankBranches)
//                    .build();