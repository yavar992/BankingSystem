package myWallets.myWallets.repository;

import myWallets.myWallets.entity.BankBranches;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BankBranchRepo extends JpaRepository<BankBranches , Long> {
    @Query(value = "SELECT * FROM bankbranches WHERE branchName =?1",nativeQuery = true)
    List <BankBranches> findByName(String branchName);

    @Query(value = " SELECT * FROM bankbranches WHERE branchEmail =?1 OR branchPhoneNumber=?2" , nativeQuery = true)
    BankBranches findByBankBranchCode(String branchEmail, String branchPhoneNumber);

    @Query(value = "SELECT * FROM `bankbranches` WHERE IFSCCode =?1",nativeQuery = true)
    Optional<BankBranches> findByIFSCCode(String ifscCode);
}
