package myWallets.myWallets.repository;

import myWallets.myWallets.entity.CustomerAccountDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerAccountDetailsRepo extends JpaRepository<CustomerAccountDetails , Long> {
}
