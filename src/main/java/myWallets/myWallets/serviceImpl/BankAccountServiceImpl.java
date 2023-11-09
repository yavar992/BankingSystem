package myWallets.myWallets.serviceImpl;

import lombok.extern.slf4j.Slf4j;
import myWallets.myWallets.DTO.BankAccountDTO;
import myWallets.myWallets.entity.BankAccount;
import myWallets.myWallets.entity.CurrentUserSession;
import myWallets.myWallets.entity.Customer;
import myWallets.myWallets.exceptionHandling.BankNotFoundException;
import myWallets.myWallets.exceptionHandling.UserNotFoundException;
import myWallets.myWallets.repository.BankAccountRepo;
import myWallets.myWallets.repository.BankBranchRepo;
import myWallets.myWallets.repository.CurrentUserSessionRepo;
import myWallets.myWallets.repository.CustomerRepo;
import myWallets.myWallets.service.BankAccountService;
import myWallets.myWallets.validator.ValidatorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class BankAccountServiceImpl implements BankAccountService {

    @Autowired
    private BankAccountRepo bankAccountRepo;

    @Autowired
    CurrentUserSessionRepo currentUserSessionRepo;

    @Autowired
    CustomerRepo customerRepo;

    @Autowired
    BankBranchRepo bankBranchRepo;

    public BankAccountServiceImpl(BankAccountRepo bankAccountRepo) {
        this.bankAccountRepo = bankAccountRepo;
    }

    @Override
    public String openBankAccount(String uuid, BankAccountDTO bankAccountDTO) {
        try {
            Optional<CurrentUserSession> currentUserSession1 = currentUserSessionRepo.findByUUID(uuid);
            if (currentUserSession1.isEmpty()) {
                throw new UserNotFoundException("User Not Found for UUID " + uuid);
            }
            Optional<Customer> customer = customerRepo.findByUUID(uuid);
            if (customer.isEmpty()){
                throw new UserNotFoundException("User Not Found for UUID " + uuid);
            }
//            Customer customer1 = customer.get();
//            List<BankBranches> bankBranches = bankBranchRepo.findByName(bankAccountDTO.getBranchName());
//            log.info("Bank Branch " + bankBranches);
//            String branchCode = bankBranches.get(0).getBranchCode();
//            log.info("Bank Branch " +branchCode);
//
//            if (bankBranches==null || bankBranches.isEmpty()){
//                throw new BankBranchesNotFoundException("No branches found for bank " + bankAccountDTO.getBranchName());
//            }
//
//            BankAccount bankAccount = BankAccount.builder()
//                    .accountType(bankAccountDTO.getAccountType().toUpperCase())
//                    .accountHolderName(bankAccountDTO.getAccountHolderName())
//                    .balance(bankAccountDTO.getBalance()!=0 ? bankAccountDTO.getBalance() :0)
//                    .bankBranches(bankBranches)
//                    .accountNo("HYBK0"+branchCode+ Validator.accountLast4digits())
//                    .bankIdentificationNumber("HYBK")
//                    .bankName("HAPPY BANK")
//                    .Status("ACTIVE")
//                    .currency("INR")
//                    .accountOpeningDate(ZonedDateTime.now())
//                    .accountCloseDate(null)
//                    .build();
            BankAccount bankAccount1;
//                    bankAccountRepo.saveAndFlush();
            return "";
        }catch (Exception e){
            log.info("cannot open the bank account due to " + e.getMessage());
            throw e;
        }
    }

    @Override
    public List<BankAccount> findBankAccount() {
        try {
            List<BankAccount> bankAccount = bankAccountRepo.findAllBankAccounts();
            log.info("bank account " + bankAccount);
            if (bankAccount==null || bankAccount.isEmpty()){
                throw new BankNotFoundException("Bank account not found");
            }
            return bankAccount;
        }catch (Exception e){
            throw e;
        }
    }

    @Override
    public List<BankAccount> getllBanks() {
         return bankAccountRepo.findAll();

    }

    @Override
    public BankAccount findBankAccountById(Long id) {
        try {
            Optional<BankAccount> bankAccount = bankAccountRepo.findById(id);
            ValidatorUtils.validateBank(bankAccount);
            BankAccount bankAccount1 = bankAccount.get();
            log.info("BankAccount" + bankAccount1);
            return bankAccount1;
        }catch (Exception e){
            throw e;
        }
    }

    public BankAccount findAccountById(Long id){
        BankAccount bankAccount = bankAccountRepo.findBankAccountById(id);
        log.info("bankAccount" + bankAccount);
        if (bankAccount==null){
            throw new BankNotFoundException("Bank Not Found");
        }
        return bankAccount;
    }


}
