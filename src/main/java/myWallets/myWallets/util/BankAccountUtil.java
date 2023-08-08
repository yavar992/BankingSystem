package myWallets.myWallets.util;

import myWallets.myWallets.DTO.BankAccountDTO;
import myWallets.myWallets.entity.BankAccount;
import myWallets.myWallets.entity.CustomerAccountDetails;

public interface BankAccountUtil {
    CustomerAccountDetails openAccount(String uuid, BankAccountDTO bankAccountDTO);
}
