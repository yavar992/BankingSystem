package myWallets.myWallets.service;

import myWallets.myWallets.DTO.BeneficiaryDTO;
import myWallets.myWallets.DTO.BeneficiaryVerififyAccountDTO;

public interface BeneficiaryService {
    String registerdBeneficiary(String uuid, BeneficiaryDTO beneficiaryDTO, String bankAccountNumber);

    boolean beneficiaryAlreadyExists(String email, String phoneNumber);

    boolean findAllBeneficiaryAccount();

    String verifyBeneficiaryAccount(String uuid, String bankAccountNumber, BeneficiaryVerififyAccountDTO beneficiaryVerififyAccountDTO);
}
