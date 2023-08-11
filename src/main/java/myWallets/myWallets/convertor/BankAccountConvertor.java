package myWallets.myWallets.convertor;

import myWallets.myWallets.DTO.BankAccountDTO;
import myWallets.myWallets.entity.BankAccount;
import myWallets.myWallets.entity.BankAccountType;
import myWallets.myWallets.entity.CustomerAccountDetails;

public class BankAccountConvertor {

    public static CustomerAccountDetails convertBankAccountDtoToBankAccount(BankAccountDTO bankAccountDTO){
        CustomerAccountDetails customerAccountDetails = CustomerAccountDetails.builder()
                .accountHolderName(bankAccountDTO.getAccountHolderName())
                .bankAccountType(bankAccountDTO.getBankAccountType())
                .balance(bankAccountDTO.getBalance())
                .build();
        return customerAccountDetails;
    }
}
