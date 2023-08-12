package myWallets.myWallets.serviceImpl;

import lombok.extern.slf4j.Slf4j;
import myWallets.myWallets.DTO.CustomerAccountDetailsDTO;
import myWallets.myWallets.DTO.TransactionDTO;
import myWallets.myWallets.DTO.TransactionDebitDTO;
import myWallets.myWallets.constant.HappyBankUtilMethods;
import myWallets.myWallets.entity.BankBranches;
import myWallets.myWallets.entity.CustomerAccountDetails;
import myWallets.myWallets.entity.Transaction;
import myWallets.myWallets.exceptionHandling.*;
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
}
