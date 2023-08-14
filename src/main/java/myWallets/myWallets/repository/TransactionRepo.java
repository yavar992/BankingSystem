package myWallets.myWallets.repository;

import myWallets.myWallets.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TransactionRepo extends JpaRepository<Transaction , Long> {
    @Query(value = "SELECT * FROM `transaction` WHERE accountNumber = ?1",nativeQuery = true)
    List<Transaction> findByAccountNumber(String accountNumber);
}
