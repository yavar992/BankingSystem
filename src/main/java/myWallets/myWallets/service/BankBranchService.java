package myWallets.myWallets.service;

import myWallets.myWallets.DTO.BankBranchSendarDTO;
import myWallets.myWallets.entity.BankBranches;

import javax.security.auth.login.LoginException;
import java.util.List;

public interface BankBranchService {
    List<BankBranches> getAllBranches(String uuid) throws LoginException;

     List<BankBranches> getBankBranchesByBankBranchName(String uuid, String branchName);

    Long getBranchCodeByBranchName(String branchName, String uuid) throws LoginException;

    void saveBranchesToBank(BankBranches addBranchToBank);

    BankBranchSendarDTO getBranchesByBranchId(String uuid, Long id);

    BankBranches bankBranchesById(Long id);

    List<BankBranchSendarDTO> getALLbranchesData(String uuid);

    BankBranchSendarDTO findBankBranchById(String uuid, Long id);

    void updateBankBranch(BankBranches updateMessage);

    boolean branchAlreadyExist(String branchEmail, String branchPhoneNumber);

    BankBranches getBankBranchesByIFSCCode(String uuid, String ifscCode);
}
