package myWallets.myWallets.service;

import myWallets.myWallets.DTO.TransactionDTO;
import myWallets.myWallets.DTO.TransactionDebitDTO;
import myWallets.myWallets.DTO.WithdrawalMoneyByAtmDTO;
import myWallets.myWallets.entity.CustomerAccountDetails;
import myWallets.myWallets.entity.Transaction;

import java.util.List;

public interface TransactionService {
    String creditMoneyToAccount(String uuid, TransactionDTO transactionDTO);

    String debitMoneyFromAccount(String uuid, TransactionDTO transactionDebitDTO);

    String transfarMoney(String uuid, String customerAccountId, TransactionDTO transactionDTO);

    String makePaymentByATM(String uuid, WithdrawalMoneyByAtmDTO withdrawalMoneyByAtmDTO);

    CustomerAccountDetails getAccountDetailsByCardNumber(String cardNumber);

    List<Transaction> getAllTransaction();

    List<Transaction> getTransactionsByAccountNumber(String uuid, String accountNumber);
}
