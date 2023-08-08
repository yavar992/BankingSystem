package myWallets.myWallets.util;

import myWallets.myWallets.DTO.BankBranchDTO;
import myWallets.myWallets.entity.BankBranches;

public interface BankBranchesUtil {
    BankBranches addBranchesToBank(String uuid, BankBranchDTO bankBranchDTO);
}
