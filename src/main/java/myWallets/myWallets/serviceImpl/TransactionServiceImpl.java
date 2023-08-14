package myWallets.myWallets.serviceImpl;

import lombok.extern.slf4j.Slf4j;
import myWallets.myWallets.DTO.CustomerAccountDetailsDTO;
import myWallets.myWallets.DTO.TransactionDTO;
import myWallets.myWallets.DTO.TransactionDebitDTO;
import myWallets.myWallets.DTO.WithdrawalMoneyByAtmDTO;
import myWallets.myWallets.constant.HappyBankUtilMethods;
import myWallets.myWallets.entity.*;
import myWallets.myWallets.exceptionHandling.*;
import myWallets.myWallets.repository.AtmRepository;
import myWallets.myWallets.repository.BankBranchRepo;
import myWallets.myWallets.repository.CustomerAccountDetailsRepo;
import myWallets.myWallets.repository.TransactionRepo;
import myWallets.myWallets.service.BankBranchService;
import myWallets.myWallets.service.TransactionService;
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
}

