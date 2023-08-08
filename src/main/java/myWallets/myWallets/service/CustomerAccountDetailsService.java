package myWallets.myWallets.service;

import myWallets.myWallets.DTO.BankAccountDTO;
import myWallets.myWallets.entity.BankAccount;
import myWallets.myWallets.entity.CustomerAccountDetails;

public interface CustomerAccountDetailsService {

    CustomerAccountDetails openAccountToBank(CustomerAccountDetails bankAccount);

    CustomerAccountDetails openAccount(String uuid, BankAccountDTO bankAccountDTO);
}
