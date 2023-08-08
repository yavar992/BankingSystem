package myWallets.myWallets.service;

import myWallets.myWallets.DTO.BankAccountDTO;
import myWallets.myWallets.entity.BankAccount;

import java.util.List;

public interface BankAccountService {
    String openBankAccount(String uuid, BankAccountDTO bankAccountDTO);

     List<BankAccount> findBankAccount();

    List<BankAccount> getllBanks();

    BankAccount findBankAccountById(Long id);

    BankAccount findAccountById(Long id);
}
