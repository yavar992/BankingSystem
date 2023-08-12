package myWallets.myWallets.service;

import myWallets.myWallets.DTO.BankAccountDTO;
import myWallets.myWallets.DTO.CustomerAccountDetailsDTO;
import myWallets.myWallets.DTO.CustomerAccountRecieveDTO;
import myWallets.myWallets.DTO.CustomerAllDetails;
import myWallets.myWallets.entity.BankAccountType;
import myWallets.myWallets.entity.CustomerAccountDetails;

import java.util.List;

public interface CustomerAccountDetailsService {

    CustomerAccountDetails openAccountToBank(CustomerAccountDetails bankAccount);

    CustomerAccountDetails openAccount(String uuid, BankAccountDTO bankAccountDTO);

    void saveCustomer(CustomerAccountDetails bankAccount);

    boolean accountAlreadyExist(String bankAccountType);

    List<CustomerAccountDetailsDTO> findCustomerAccountDetails(String uuid, Long customerId);

    CustomerAccountDetailsDTO findCustomerByAccountNo(String uuid, String accountNo);
}
