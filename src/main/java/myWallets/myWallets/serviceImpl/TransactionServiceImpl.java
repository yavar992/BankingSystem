package myWallets.myWallets.serviceImpl;

import com.ctc.wstx.shaded.msv_core.reader.relax.core.ElementRuleWithHedgeState;
import lombok.extern.slf4j.Slf4j;
import myWallets.myWallets.DTO.*;
import myWallets.myWallets.constant.HappyBankUtilMethods;
import myWallets.myWallets.entity.*;
import myWallets.myWallets.exceptionHandling.*;
import myWallets.myWallets.repository.*;
import myWallets.myWallets.service.BankBranchService;
import myWallets.myWallets.service.TransactionService;
import myWallets.myWallets.service.WalletService;
import myWallets.myWallets.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Slf4j
@Transactional
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepo transactionRepo;

    @Autowired
    BankBranchRepo bankBranchRepo;

    @Autowired
    HappyBankUtilMethods happyBankUtilMethods;

    @Autowired
    WalletRepo walletRepo;

    @Autowired
    CustomerAccountDetailsRepo customerAccountDetailsRepo;

    @Autowired
    AtmRepository atmRepository;

    @Override
    public String creditMoneyToAccount(String uuid, TransactionDTO transactionDTO) {
        try {
            log.info("transactionDTO: " + transactionDTO);
            happyBankUtilMethods.authorizeAndGetVerifiedCustomer(uuid);
             BankBranches bankBranches = happyBankUtilMethods.validateBankBranch(transactionDTO.getIfscCode());
            CustomerAccountDetails customerAccountDetails = happyBankUtilMethods.validateCustomerAccountDetails(transactionDTO.getAccountNumber());
            Double amount = customerAccountDetails.getBalance();
            String IFSCCode = bankBranches.getIFSCCode();
            String accountNumber = customerAccountDetails.getAccountNo();
            if (!accountNumber.equals(transactionDTO.getAccountNumber()) || !IFSCCode.equals(transactionDTO.getIfscCode())){
                throw new TransactionException("Invalid Details either accountNumber or IFSCCode is incorrect ");
            }
            if (accountNumber.equals(transactionDTO.getAccountNumber()) && IFSCCode.equals(transactionDTO.getIfscCode())){
                customerAccountDetails.setBalance(amount+transactionDTO.getAmount());
                customerAccountDetailsRepo.saveAndFlush(customerAccountDetails);
                Transaction transaction = new Transaction();
                transaction.setTransactionDate(LocalDate.now());
                transaction.setAmount(transactionDTO.getAmount());
                transaction.setTransactionType("Deposit");
                transaction.setTransactionId(Validator.generateTransactionId());
                transaction.setAccountNumber(transactionDTO.getAccountNumber());
                transaction.setDescription(transactionDTO.getDescription());
                transaction.setCustomerAccountDetails(customerAccountDetails);
                transactionRepo.saveAndFlush(transaction);
            }
            return "Transaction created successfully";
        }catch (Exception e){
            throw e;
        }
    }

    @Override
    public String debitMoneyFromAccount(String uuid, TransactionDTO transactionDebitDTO) {
        try {
            happyBankUtilMethods.authorizeAndGetVerifiedCustomer(uuid);
         CustomerAccountDetails customerAccountDetails = happyBankUtilMethods.validateCustomerAccountDetails(transactionDebitDTO.getAccountNumber());
         BankBranches bankBranches = happyBankUtilMethods.validateBankBranch(transactionDebitDTO.getIfscCode());
            Double amount = customerAccountDetails.getBalance();
            String IFSCCode = bankBranches.getIFSCCode();
            String accountNumber = customerAccountDetails.getAccountNo();
            if (!accountNumber.equals(transactionDebitDTO.getAccountNumber()) || !IFSCCode.equals(transactionDebitDTO.getIfscCode())){
                throw new TransactionException("Invalid Details either accountNumber or IFSCCode is incorrect ");
            }
            if (transactionDebitDTO.getAmount()>amount){
                throw new InsufficientBalanceException("Insufficient Balance");
            }

            if (accountNumber.equals(transactionDebitDTO.getAccountNumber()) && IFSCCode.equals(transactionDebitDTO.getIfscCode())){
                customerAccountDetails.setBalance(amount-transactionDebitDTO.getAmount());
                customerAccountDetailsRepo.saveAndFlush(customerAccountDetails);
                Transaction transaction = new Transaction();
                transaction.setTransactionDate(LocalDate.now());
                transaction.setAmount(transactionDebitDTO.getAmount());
                transaction.setTransactionType("Withdrawal");
                transaction.setTransactionId(Validator.generateTransactionId());
                transaction.setAccountNumber(transactionDebitDTO.getAccountNumber());
                transaction.setDescription(transactionDebitDTO.getDescription());
                transaction.setCustomerAccountDetails(customerAccountDetails);
                transactionRepo.saveAndFlush(transaction);
            }
            return "money Withdrawal successfully";
        }catch (Exception e){
            throw e;
        }
    }

    @Override
    public String transfarMoney(String uuid, String customerAccountId, TransactionDTO transactionDTO) {
        try {
            happyBankUtilMethods.authorizeAndGetVerifiedCustomer(uuid);
          CustomerAccountDetails customerAccountDetails = happyBankUtilMethods.validateCustomerAccountDetails(customerAccountId);
          Double balance = customerAccountDetails.getBalance();
          String customerAccountNumber = customerAccountDetails.getAccountNo();
          CustomerAccountDetails customerAccountDetails1 = happyBankUtilMethods.validateCustomerAccountDetails(transactionDTO.getAccountNumber());
          String customerAccountNumber2 = customerAccountDetails1.getAccountNo();
          Double recieverBalance = customerAccountDetails1.getBalance();
          if (customerAccountNumber.equals(customerAccountNumber2)){
              throw new IncorrectAccountNumber("Plz enter the correct AccountNumber ");
          }
         BankBranches bankBranches = happyBankUtilMethods.validateBankBranch(transactionDTO.getIfscCode());
          String IFSCCode = bankBranches.getIFSCCode();
           if (!customerAccountNumber2.equals(transactionDTO.getAccountNumber()) || !IFSCCode.equals(transactionDTO.getIfscCode())){
               throw new TransactionException("Invalid Details either accountNumber or IFSCCode is incorrect");
           }
            if (transactionDTO.getAmount()>balance){
                throw new InsufficientBalanceException("Insufficient Balance");
            }
            if (customerAccountNumber2.equals(transactionDTO.getAccountNumber()) && IFSCCode.equals(transactionDTO.getIfscCode())){
                    balance = balance - transactionDTO.getAmount();
                    customerAccountDetails.setBalance(balance);
                    customerAccountDetailsRepo.saveAndFlush(customerAccountDetails);
                    customerAccountDetails1.setBalance(transactionDTO.getAmount());
                    customerAccountDetailsRepo.saveAndFlush(customerAccountDetails1);
                    Transaction transaction = new Transaction();
                    transaction.setTransactionDate(LocalDate.now());
                    transaction.setAmount(transactionDTO.getAmount());
                    transaction.setTransactionType("Transfer");
                    transaction.setTransactionId(Validator.generateTransactionId());
                    transaction.setAccountNumber(transactionDTO.getAccountNumber());
                    transaction.setDescription(transactionDTO.getDescription());
                    transaction.setCustomerAccountDetails(customerAccountDetails);
                    transactionRepo.saveAndFlush(transaction);
            }
            return "Money transfer successfully";
        }catch (Exception e){
            throw e;
        }
    }

    @Override
    public String makePaymentByATM(String uuid, WithdrawalMoneyByAtmDTO withdrawalMoneyByAtmDTO) {
        happyBankUtilMethods.authorizeAndGetVerifiedCustomer(uuid);
       String atmNumber = withdrawalMoneyByAtmDTO.getCardNumber();
       ATM atm = atmRepository.findByCardNumber(atmNumber);
       log.info("ATM ::" + atm);
       if (atm==null){
           throw new ATMNotFound("No card number found for cardNumber " + atmNumber);
       }
        CustomerAccountDetails customerAccountDetails = getAccountDetailsByCardNumber(atmNumber);
       log.info("customerAccountDetails " + customerAccountDetails);
       Double balance = customerAccountDetails.getBalance();
        Boolean isVerified = atm.isVerified();
        if (!isVerified){
            throw new InvalidAtmDetails("Plz verified you account first for generating your ATM Pin ");
        }
       LocalDate expirationDate = atm.getAtmExpirationDate();
       String cvv = atm.getCvv();
       if (expirationDate.compareTo(withdrawalMoneyByAtmDTO.getAtmExpirationDate())<0){
            throw new CardExpirationException("your atm card has expired plz apply for new card");
       }

       if (!cvv.equals(withdrawalMoneyByAtmDTO.getCvv()) ||
               !expirationDate.equals(withdrawalMoneyByAtmDTO.getAtmExpirationDate())
                || !atm.getCardNumber().equals(withdrawalMoneyByAtmDTO.getCardNumber())
       ){
           throw new InvalidAtmDetails("Invalid card data");
       }
       if (cvv.equals(withdrawalMoneyByAtmDTO.getCvv()) &&
               atm.getCardNumber().equals(withdrawalMoneyByAtmDTO.getCardNumber()) &&
               expirationDate.equals(withdrawalMoneyByAtmDTO.getAtmExpirationDate())
       ){
           Transaction transaction = new Transaction();
           if (withdrawalMoneyByAtmDTO.getBalance()>balance){
               throw new InsufficientBalanceException("InSufficient Balance");
           }
           balance = balance- withdrawalMoneyByAtmDTO.getBalance();
           customerAccountDetails.setBalance(balance);
           customerAccountDetailsRepo.saveAndFlush(customerAccountDetails);
           transaction.setTransactionDate(LocalDate.now());
           transaction.setTransactionType("ATMCard");
           transaction.setDescription(withdrawalMoneyByAtmDTO.getDescription());
           transaction.setAmount(withdrawalMoneyByAtmDTO.getBalance());
           transaction.setTransactionId(Validator.generateTransactionId());
           transaction.setCustomerAccountDetails(customerAccountDetails);
           transaction.setAccountNumber(customerAccountDetails.getAccountNo());
           transactionRepo.saveAndFlush(transaction);
       }
        return "transaction Successfully";
    }

    @Override
    public CustomerAccountDetails getAccountDetailsByCardNumber(String cardNumber) {
        ATM atm = atmRepository.findByCardNumber(cardNumber);
        if (atm==null){
            throw new ATMNotFound("No such ATM Card exist for card number " + cardNumber);
        }
        CustomerAccountDetails customerAccountDetails = atm.getCustomerAccountDetails();
        if (customerAccountDetails==null){
            throw new CustomerAccountException("No such Customer Account found for card number " + cardNumber);
        }
        return customerAccountDetails;
    }

    @Override
    public List<Transaction> getAllTransaction() {
        List<Transaction> transactions = transactionRepo.findAll();
        if (transactions == null) {
            throw new NoTransactionException("Empty list of transactions");
        }
        return transactions;
    }

    @Override
    public List<Transaction> getTransactionsByAccountNumber(String uuid, String accountNumber) {
        happyBankUtilMethods.authorizeAndGetVerifiedCustomer(uuid);
        List<Transaction> transactions = transactionRepo.findByAccountNumber(accountNumber);
        if (transactions == null || transactions.isEmpty())  {
            throw new  NoTransactionException("No transaction found for accountNumber " + accountNumber);
        }
        return transactions;
    }

    @Override
    public List<Transaction> getTransactionBetweenDates(String uuid, String accountNumber, LocalDate startingDate, LocalDate endDate) {
        happyBankUtilMethods.authorizeAndGetVerifiedCustomer(uuid);
        List<Transaction> transactions = transactionRepo.findByTransactionStartAndEndDate(accountNumber ,startingDate ,endDate);
        if (transactions == null || transactions.isEmpty())  {
            throw new  NoTransactionException("No transaction found for accountNumber " + accountNumber);
        }
        return transactions;
    }

    @Override
    public String debitMoneyByBeneficiary(String uuid,  TransactionDTO beneficiaryTransactionDTO) {
        happyBankUtilMethods.authorizeAndGetVerifiedCustomer(uuid);
        CustomerAccountDetails customerAccountDetails = happyBankUtilMethods.validateCustomerAccountDetails(beneficiaryTransactionDTO.getAccountNumber());
        Beneficiary beneficiary = happyBankUtilMethods.validateBeneficiaryAccount(customerAccountDetails);
        BankBranches bankBranches = happyBankUtilMethods.validateBankBranch(beneficiaryTransactionDTO.getIfscCode());
        String IFSCCode = bankBranches.getIFSCCode();
        Double balance = customerAccountDetails.getBalance();
        Boolean isAccountVerified = beneficiary.isAccountVerified();
        log.info("isAccountVerified: " + isAccountVerified);
        String accountNumber = beneficiaryTransactionDTO.getAccountNumber();
        Boolean allowedToWithdrawal = beneficiary.isAllowedToWithdraw();
        log.info("allowedToWithdrawal: " +allowedToWithdrawal);
        if (!isAccountVerified){
            throw new BeneficiaryException("Your account is not verified yet plz verified your account in process to withdrawal money ");
        }
        if (!isAccountVerified || !allowedToWithdrawal){
            throw new BeneficiaryException("You have not permission to withdraw money from owner account plz submit your document in banks to verified yourself ");
        }
        if (!accountNumber.equals(beneficiaryTransactionDTO.getAccountNumber()) || !IFSCCode.equals(beneficiaryTransactionDTO.getIfscCode())){
            throw new TransactionException("Invalid Details either accountNumber or IFSCCode is incorrect ");
        }
        if (allowedToWithdrawal && isAccountVerified &&  accountNumber.equals(beneficiaryTransactionDTO.getAccountNumber()) && IFSCCode.equals(beneficiaryTransactionDTO.getIfscCode())){
            customerAccountDetails.setBalance(balance-beneficiaryTransactionDTO.getAmount());
            customerAccountDetailsRepo.saveAndFlush(customerAccountDetails);
            Transaction transaction = new Transaction();
            transaction.setTransactionDate(LocalDate.now());
            transaction.setAmount(beneficiaryTransactionDTO.getAmount());
            transaction.setTransactionType("BENEFICIARY");
            transaction.setTransactionId(Validator.generateTransactionId());
            transaction.setAccountNumber(beneficiaryTransactionDTO.getAccountNumber());
            transaction.setDescription(beneficiaryTransactionDTO.getDescription());
            transaction.setCustomerAccountDetails(customerAccountDetails);
            transactionRepo.saveAndFlush(transaction);
        }
        return "Transaction created successfully";
    }

    @Override
    public String addMoneyToWallet(String uuid, TransactionDTO transactionDTO) {
        happyBankUtilMethods.authorizeAndGetVerifiedCustomer(uuid);
        CustomerAccountDetails customerAccountDetails = happyBankUtilMethods.validateCustomerAccountDetails(transactionDTO.getAccountNumber());
        Wallet wallet = customerAccountDetails.getWallet();
        Double walletBalance = wallet.getBalance();
        if (wallet==null){
            throw new WalletException("No Wallet found for for " +transactionDTO.getAmount());
        }
        Double balance = customerAccountDetails.getBalance();
        BankBranches bankBranches = happyBankUtilMethods.validateBankBranch(transactionDTO.getIfscCode());
        if (balance<transactionDTO.getAmount()){
            throw new InsufficientBalanceException("InSufficient Balance ");
        }
        if (!customerAccountDetails.getAccountNo().equals(transactionDTO.getAccountNumber()) &&
                !bankBranches.getIFSCCode().equals(transactionDTO.getIfscCode())){
            throw new CustomerAccountException("Incorrect Customer Account Details");
        }
        if (customerAccountDetails.getAccountNo().equals(transactionDTO.getAccountNumber()) &&
                bankBranches.getIFSCCode().equals(transactionDTO.getIfscCode())){
            customerAccountDetails.setBalance(balance-transactionDTO.getAmount());
            customerAccountDetailsRepo.saveAndFlush(customerAccountDetails);
            wallet.setBalance(walletBalance+transactionDTO.getAmount());
            walletRepo.saveAndFlush(wallet);
            Transaction transaction = new Transaction();
            transaction.setTransactionDate(LocalDate.now());
            transaction.setAmount(transactionDTO.getAmount());
            transaction.setTransactionType("WALLET");
            transaction.setTransactionId(Validator.generateTransactionId());
            transaction.setAccountNumber(transactionDTO.getAccountNumber());
            transaction.setDescription(transactionDTO.getDescription());
            transaction.setCustomerAccountDetails(customerAccountDetails);
            transactionRepo.saveAndFlush(transaction);
        }
        return "Balance successfully added to the wallet";
    }

    @Override
    public String transferMoneyToBankFromWallet(String uuid, String recieverAccountNumber, TransactionDTO transactionDTO) {
        happyBankUtilMethods.authorizeAndGetVerifiedCustomer(uuid);
        String accountNumber = transactionDTO.getAccountNumber();
        CustomerAccountDetails customerAccountDetails = happyBankUtilMethods.validateCustomerAccountDetails(accountNumber);
        Wallet wallet = customerAccountDetails.getWallet();
        if (wallet==null){
            throw new WalletException("No wallet found for account " + accountNumber);
        }
        CustomerAccountDetails recieverAccount = happyBankUtilMethods.validateCustomerAccountDetails(recieverAccountNumber);
        Double recieverAccountBalance = recieverAccount.getBalance();
        Double walletBalance = wallet.getBalance();
        BankBranches bankBranches = happyBankUtilMethods.validateBankBranch(transactionDTO.getIfscCode());
        if (walletBalance<transactionDTO.getAmount()){
            throw new InsufficientBalanceException("InSufficient Balance ");
        }
        if (!customerAccountDetails.getAccountNo().equals(transactionDTO.getAccountNumber()) &&
                !bankBranches.getIFSCCode().equals(transactionDTO.getIfscCode())){
            throw new CustomerAccountException("Incorrect Customer Account Details");
        }
        if (customerAccountDetails.getAccountNo().equals(transactionDTO.getAccountNumber()) &&
                bankBranches.getIFSCCode().equals(transactionDTO.getIfscCode())){
            recieverAccount.setBalance(recieverAccountBalance+transactionDTO.getAmount());
            customerAccountDetailsRepo.saveAndFlush(recieverAccount);
            wallet.setBalance(walletBalance-transactionDTO.getAmount());
            walletRepo.saveAndFlush(wallet);
            Transaction transaction = new Transaction();
            transaction.setTransactionDate(LocalDate.now());
            transaction.setAmount(transactionDTO.getAmount());
            transaction.setTransactionType("WALLET_TRANSFER");
            transaction.setTransactionId(Validator.generateTransactionId());
            transaction.setAccountNumber(transactionDTO.getAccountNumber());
            transaction.setDescription(transactionDTO.getDescription());
            transaction.setCustomerAccountDetails(customerAccountDetails);
            transactionRepo.saveAndFlush(transaction);
        }
        return "transaction Successful";

    }

    @Override
    public String makePaymentByWallet(String uuid, TransactionDTO transactionDTO) {
        happyBankUtilMethods.authorizeAndGetVerifiedCustomer(uuid);
        CustomerAccountDetails customerAccountDetails = happyBankUtilMethods.validateCustomerAccountDetails(transactionDTO.getAccountNumber());
        Wallet wallet = customerAccountDetails.getWallet();
        BankBranches bankBranches = happyBankUtilMethods.validateBankBranch(transactionDTO.getIfscCode());
        Double walletBalance = wallet.getBalance();
        if (wallet == null) {
            throw new WalletException("No wallet found for account " + transactionDTO.getAccountNumber());
        }
        if (walletBalance<transactionDTO.getAmount()){
            throw new InsufficientBalanceException("InSufficient Balance ");
        }
        if (!customerAccountDetails.getAccountNo().equals(transactionDTO.getAccountNumber()) &&
                !bankBranches.getIFSCCode().equals(transactionDTO.getIfscCode())){
            throw new CustomerAccountException("Incorrect Customer Account Details");
        }
        if (customerAccountDetails.getAccountNo().equals(transactionDTO.getAccountNumber()) &&
                bankBranches.getIFSCCode().equals(transactionDTO.getIfscCode())){
            wallet.setBalance(walletBalance-transactionDTO.getAmount());
            walletRepo.saveAndFlush(wallet);
            Transaction transaction = new Transaction();
            transaction.setTransactionDate(LocalDate.now());
            transaction.setAmount(transactionDTO.getAmount());
            transaction.setTransactionType("WALLET");
            transaction.setTransactionId(Validator.generateTransactionId());
            transaction.setAccountNumber(transactionDTO.getAccountNumber());
            transaction.setDescription(transactionDTO.getDescription());
            transaction.setCustomerAccountDetails(customerAccountDetails);
            transactionRepo.saveAndFlush(transaction);
        }
        return "Wallet transaction successfully";

    }
}

