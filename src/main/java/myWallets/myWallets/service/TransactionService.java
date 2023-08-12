package myWallets.myWallets.service;

import myWallets.myWallets.DTO.TransactionDTO;
import myWallets.myWallets.DTO.TransactionDebitDTO;

public interface TransactionService {
    String creditMoneyToAccount(String uuid, TransactionDTO transactionDTO);

    String debitMoneyFromAccount(String uuid, TransactionDTO transactionDebitDTO);

    String transfarMoney(String uuid, String customerAccountId, TransactionDTO transactionDTO);
}
