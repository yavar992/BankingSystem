package myWallets.myWallets.repository;

import myWallets.myWallets.entity.BankAccount;
import myWallets.myWallets.entity.BankBranches;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BankAccountRepo extends JpaRepository<BankAccount , Long> {

    @Query(value = "SELECT id , bankName , bankIdentificationNumber , bankOpeningDate , customerSupportEmail , customerSupportNumber FROM `bankaccount` WHERE id =?1" ,nativeQuery = true)
    BankAccount findBankAccountById(Long id);

    @Query(value = "SELECT id , bankName , bankIdentificationNumber , bankOpeningDate , customerSupportEmail , customerSupportNumber FROM `bankaccount`" , nativeQuery = true)
    List<BankAccount> findAllBankAccounts();
}
