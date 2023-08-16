package myWallets.myWallets.repository;

import myWallets.myWallets.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepo extends JpaRepository<Wallet , Long> {
}
