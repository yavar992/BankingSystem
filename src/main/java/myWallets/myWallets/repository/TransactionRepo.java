package myWallets.myWallets.repository;

import myWallets.myWallets.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepo extends JpaRepository<Transaction , Long> {
}
