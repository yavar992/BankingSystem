package myWallets.myWallets.convertor;

import myWallets.myWallets.DTO.BankBranchDTO;
import myWallets.myWallets.DTO.BankBranchSendarDTO;
import myWallets.myWallets.entity.BankBranches;


public class BankBranchConvertor {

    public static BankBranches convertBankBranchDtoToBankBranches(BankBranchDTO bankBranchDTO){
        return BankBranches.builder()
                .branchEmail(bankBranchDTO.getBranchEmail())
                .branchManager(bankBranchDTO.getBranchManager().toUpperCase())
                .branchName(bankBranchDTO.getBranchName().toUpperCase())
                .branchPhoneNumber(bankBranchDTO.getBranchPhoneNumber())
                .state(bankBranchDTO.getState().toUpperCase())
                .city(bankBranchDTO.getCity().toUpperCase())
                .postalCode(bankBranchDTO.getPostalCode())
                .streetAddress(bankBranchDTO.getStreetAddress().toUpperCase())
                .build();
    }

    public static BankBranchSendarDTO convertBankBranchToBankBranchDTO(BankBranches bankBranches){
        return BankBranchSendarDTO.builder()
                .id(bankBranches.getId())
                .branchName(bankBranches.getBranchName())
                .branchPhoneNumber(bankBranches.getBranchCode())
                .city(bankBranches.getCity())
                .state(bankBranches.getState())
                .streetAddress(bankBranches.getStreetAddress())
                .build();
    }


    public static BankBranches convertBankBranchesSendarDtoToBankBranches(BankBranchSendarDTO bankBranchSendarDTO){
        return BankBranches.builder()
                .branchName(bankBranchSendarDTO.getBranchName())
                .streetAddress(bankBranchSendarDTO.getStreetAddress())
                .city(bankBranchSendarDTO.getCity())
                .state(bankBranchSendarDTO.getState())
                .branchPhoneNumber(bankBranchSendarDTO.getBranchPhoneNumber())
                .build();
    }

    public static BankBranchSendarDTO mappedToBankBranch(Object[] customerAllDetails) {
        BankBranchSendarDTO dto = new BankBranchSendarDTO();
        dto.setId((Long) customerAllDetails[4]); // Assuming the index is 4 for id
        dto.setBranchName((String) customerAllDetails[5]);
        dto.setStreetAddress((String) customerAllDetails[6]);
        dto.setCity((String) customerAllDetails[7]);
        dto.setState((String) customerAllDetails[8]);
        dto.setBranchPhoneNumber((String) customerAllDetails[9]);
        return dto;
    }
}
