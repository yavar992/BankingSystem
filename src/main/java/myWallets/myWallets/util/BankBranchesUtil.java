package myWallets.myWallets.util;

import myWallets.myWallets.DTO.BankBranchDTO;
import myWallets.myWallets.DTO.BankBranchSendarDTO;
import myWallets.myWallets.entity.BankBranches;

public interface BankBranchesUtil {
    BankBranches addBranchesToBank(String uuid, BankBranchDTO bankBranchDTO);

    BankBranches updateBankBranch(String uuid, Long id, BankBranchSendarDTO bankBranchSendarDTO);
}
