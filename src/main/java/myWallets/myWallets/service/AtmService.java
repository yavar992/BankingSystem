package myWallets.myWallets.service;

import myWallets.myWallets.DTO.ActivateAccountDTO;
import myWallets.myWallets.entity.CustomerAccountDetails;

public interface AtmService {
    String getAtm(String uuid, String accountNumber);

    String atmAlreadyExists(String accountNumber);

    String atmAlreadyExistsOrNot(String accountNumber);

    boolean atmAlreadyExistsOrNope(String accountNumber);

    String activateATM(String uuid, ActivateAccountDTO activateAccountDTO , String accountNumber);

    CustomerAccountDetails getCustomerAccountDetails(String uuid, String accountNumber);

    String generatePin(String uuid, String accountNumber, ActivateAccountDTO activateAccountDTO);
}
