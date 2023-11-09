package myWallets.myWallets.convertor;

import myWallets.myWallets.DTO.BankAccountDTO;
import myWallets.myWallets.DTO.CustomerAccountDetailsDTO;
import myWallets.myWallets.entity.BankAccountType;
import myWallets.myWallets.entity.CustomerAccountDetails;

public class BankAccountConvertor {

    public static CustomerAccountDetails convertBankAccountDtoToBankAccount(BankAccountDTO bankAccountDTO){
        return CustomerAccountDetails.builder()
                .accountHolderName(bankAccountDTO.getAccountHolderName())
                .bankAccountType(bankAccountDTO.getBankAccountType())
                .balance(bankAccountDTO.getBalance())
                .build();
    }


    public static CustomerAccountDetailsDTO convertCustomerAccountDetailsToCustomerAccountDetailsDTO(CustomerAccountDetails customerAccountDetails){
        return CustomerAccountDetailsDTO.builder()
                .accountHolderName(customerAccountDetails.getAccountHolderName())
                .accountNo(customerAccountDetails.getAccountNo())
                .balance(customerAccountDetails.getBalance())
                .bankAccountType(customerAccountDetails.getBankAccountType())
                .build();
    }

    public static CustomerAccountDetailsDTO mappedToCustomerAccountDetails(Object[] customerAllDetails) {
        CustomerAccountDetailsDTO customerAccountDetailsDTO = new CustomerAccountDetailsDTO();
        customerAccountDetailsDTO.setAccountHolderName((String) customerAllDetails[0]);
        customerAccountDetailsDTO.setAccountNo((String) customerAllDetails[1]);
        customerAccountDetailsDTO.setBalance((Double) customerAllDetails[2]);
        customerAccountDetailsDTO.setBankAccountType((BankAccountType) customerAllDetails[3]);
        return customerAccountDetailsDTO;
    }


}
