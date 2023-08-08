package myWallets.myWallets.service;

import myWallets.myWallets.DTO.BankBranchDTO;
import myWallets.myWallets.entity.BankBranches;

import javax.security.auth.login.LoginException;
import java.util.List;

public interface BankBranchService {
    List<BankBranches> getAllBranches(String uuid) throws LoginException;

     List<BankBranches> getBankBranchesByBankBranchName(String uuid, String branchName);

    Long getBranchCodeByBranchName(String branchName, String uuid) throws LoginException;

    void saveBranchesToBank(BankBranches addBranchToBank);

    BankBranches getBranchesByBranchId(String uuid, Long id);

    BankBranches bankBranchesById(Long id);
}
