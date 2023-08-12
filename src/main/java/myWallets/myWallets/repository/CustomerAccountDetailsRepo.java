package myWallets.myWallets.repository;

import myWallets.myWallets.entity.BankAccountType;
import myWallets.myWallets.entity.CustomerAccountDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CustomerAccountDetailsRepo extends JpaRepository<CustomerAccountDetails , Long> {
    @Query(value = "SELECT * FROM `customeraccountdetails` WHERE bankAccountType =?1" ,nativeQuery = true)
    CustomerAccountDetails findByAccountType(String bankAccountType);

    @Query(value = "SELECT * FROM `customeraccountdetails` WHERE customer_id =?1" , nativeQuery = true)
   Optional<CustomerAccountDetails> findByCustomerId(Long customerId);

    @Query(value = "SELECT * FROM customeraccountdetails WHERE accountNo=?1" ,nativeQuery = true)
    CustomerAccountDetails findByAccountNumber(String accountNo);
}
