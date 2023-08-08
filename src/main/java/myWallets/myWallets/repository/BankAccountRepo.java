package myWallets.myWallets.repository;

import myWallets.myWallets.entity.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BankAccountRepo extends JpaRepository<BankAccount , Long> {

    @Query(value = "SELECT * FROM `bankaccount` WHERE id =?1" ,nativeQuery = true)
    BankAccount findBankAccountById(Long id);
}
