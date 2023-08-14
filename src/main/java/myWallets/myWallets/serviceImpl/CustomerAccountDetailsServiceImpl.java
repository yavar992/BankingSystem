package myWallets.myWallets.serviceImpl;

import lombok.extern.slf4j.Slf4j;
import myWallets.myWallets.DTO.BankAccountDTO;
import myWallets.myWallets.DTO.CustomerAccountDetailsDTO;
import myWallets.myWallets.DTO.CustomerAccountRecieveDTO;
import myWallets.myWallets.DTO.CustomerAllDetails;
import myWallets.myWallets.constant.HappyBankUtilMethods;
import myWallets.myWallets.convertor.BankAccountConvertor;
import myWallets.myWallets.convertor.CustomerConvertor;
import myWallets.myWallets.entity.*;
import myWallets.myWallets.event.AccountOpenEvent;
import myWallets.myWallets.exceptionHandling.BankBranchesNotFoundException;
import myWallets.myWallets.exceptionHandling.CustomerAccountException;
import myWallets.myWallets.exceptionHandling.UserNotFoundException;
import myWallets.myWallets.repository.*;
import myWallets.myWallets.service.CustomerAccountDetailsService;
import myWallets.myWallets.service.CustomerService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
public class CustomerAccountDetailsServiceImpl implements CustomerAccountDetailsService {


    private final CustomerAccountDetailsRepo customerAccountDetailsRepo;

    private final ApplicationEventPublisher applicationEventPublisher;

    private final CurrentUserSessionRepo currentUserSessionRepo;

    private final HappyBankUtilMethods happyBankUtilMethods;

    private final CustomerRepo customerRepo;




    public CustomerAccountDetailsServiceImpl(CustomerAccountDetailsRepo customerAccountDetailsRepo,
                                             ApplicationEventPublisher applicationEventPublisher,
                                             CurrentUserSessionRepo currentUserSessionRepo,
                                             CustomerService customerService,
                                             BankBranchRepo bankBranchRepo,
                                             BankAccountRepo bankAccountRepo ,
                                             HappyBankUtilMethods happyBankUtilMethods ,
                                             CustomerRepo customerRepo
    ) {
        this.customerAccountDetailsRepo = customerAccountDetailsRepo;
        this.applicationEventPublisher = applicationEventPublisher;
        this.currentUserSessionRepo = currentUserSessionRepo;
        this.customerService = customerService;
        this.bankBranchRepo = bankBranchRepo;
        this.bankAccountRepo = bankAccountRepo;
        this.happyBankUtilMethods =happyBankUtilMethods;
        this.customerRepo = customerRepo;
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
//                happyBankUtilMethods.authorizeAndGetVerifiedCustomer(uuid);
//                List<BankAccount> bankAccount = bankAccountRepo.findAll();
//                BankAccount bankAccount1 = bankAccount.get(0);
//                BankBranches bankBranches = bankBranchRepo.findById(bankAccountDTO.getBranchId())
//                        .orElseThrow(()->new BankBranchesNotFoundException("Branch Not Found"));
//             log.info("bankBranches " + bankBranches);
//            CustomerAccountDetails customerAccountDetails = CustomerAccountDetails.builder()
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
//            return customerAccountDetailsRepo.saveAndFlush(customerAccountDetails);
        }catch (Exception e){
            log.error("An error occurred while opening an account: " + e.getMessage());
            throw e;
        }
        return null;
    }

    @Override
    public void saveCustomer(CustomerAccountDetails bankAccount) {

        String customerEmail = bankAccount.getCustomer().getEmail();
        BankAccountType newAccountType = bankAccount.getBankAccountType();
        // Check if the customer already has an account of the same type
        boolean accountAlreadyExists = accountAlreadyExist(customerEmail, newAccountType);
        if (accountAlreadyExists) {
            throw new CustomerAccountException("you already have a saving type account in our bank plz continue with old account or you can open new deposit or any other type bank in our bank");
        }
        AccountOpenEvent accountOpenEvent = new AccountOpenEvent(bankAccount);
        applicationEventPublisher.publishEvent(accountOpenEvent);
        customerAccountDetailsRepo.saveAndFlush(bankAccount);
    }

    @Override
    public boolean accountAlreadyExist(String bankAccountType) {
        CustomerAccountDetails customerAccountDetails = customerAccountDetailsRepo.findByAccountType(bankAccountType);
        log.info("customerAccountDetails " + customerAccountDetails);
        return customerAccountDetails!=null;
    }
    public boolean accountAlreadyExist(String customerEmail, BankAccountType bankAccountType) {
        CustomerAccountDetails existingAccount = customerAccountDetailsRepo.findByCustomerEmailAndBankAccountType(customerEmail, bankAccountType);
        return existingAccount != null;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomerAccountDetailsDTO> findCustomerAccountDetails(String uuid, Long customerId) {
        try {
          happyBankUtilMethods.authorizeAndGetVerifiedCustomer(uuid);
            Optional<CustomerAccountDetails> customerAccountDetails = customerAccountDetailsRepo.findByCustomerId(customerId);
            if (customerAccountDetails==null || customerAccountDetails.isEmpty()){
                throw new CustomerAccountException("No account exists for the customerId" + customerId);
            }
            return customerAccountDetails.stream().map(BankAccountConvertor::convertCustomerAccountDetailsToCustomerAccountDetailsDTO).collect(Collectors.toList());
        }catch (Exception e){
            throw e;
        }
    }

    @Override
    public CustomerAccountDetailsDTO findCustomerByAccountNo(String uuid, String accountNo) {
        try {
            happyBankUtilMethods.authorizeAndGetVerifiedCustomer(uuid);
            CustomerAccountDetails customerAccountDetails = customerAccountDetailsRepo.findByAccountNumber(accountNo);
            if (customerAccountDetails==null){
                throw new CustomerAccountException("No account found for accountNo " + accountNo);
            }
            return BankAccountConvertor.convertCustomerAccountDetailsToCustomerAccountDetailsDTO(customerAccountDetails);
        }catch (Exception e){
            throw e;
        }
    }


}
