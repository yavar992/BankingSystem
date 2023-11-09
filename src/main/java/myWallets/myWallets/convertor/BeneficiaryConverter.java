package myWallets.myWallets.convertor;

import myWallets.myWallets.DTO.BeneficiaryDTO;
import myWallets.myWallets.entity.Beneficiary;

import java.time.LocalDate;

public class BeneficiaryConverter {


    public static Beneficiary convertBeneficiaryDtoToBeneficiary(BeneficiaryDTO beneficiaryDTO){
        return Beneficiary.builder()
                .phoneNumber(beneficiaryDTO.getPhoneNumber())
                .email(beneficiaryDTO.getEmail())
                .name(beneficiaryDTO.getName())
                .relationship(beneficiaryDTO.getRelationship())
                .address(beneficiaryDTO.getAddress())
                .beneficiaryAddedTime(LocalDate.now())
                .build();
    }

    public static BeneficiaryDTO convertBeneficiaryToBeneficiaryDTO(Beneficiary beneficiary){
        return BeneficiaryDTO.builder()
                .name(beneficiary.getName())
                .email(beneficiary.getEmail())
                .relationship(beneficiary.getRelationship())
                .address(beneficiary.getAddress())
                .phoneNumber(beneficiary.getPhoneNumber())
                .build();
    }
}
