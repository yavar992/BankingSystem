package myWallets.myWallets.repository;

import myWallets.myWallets.entity.ATM;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AtmRepository extends JpaRepository<ATM , Long> {
    @Query(value = "SELECT * FROM `atm` WHERE customerAccountDetails_id=?1" , nativeQuery = true)
    List<ATM> findByCustomerAccountDetailsId(Long customerAccountDetailsId);

    @Query(value = "SELECT a.id , atmExpirationDate ,atmIssueAt , cardNumber , customerNameOnATM , cvv , isVerified , pin , atmOtp , customerAccountDetails_id FROM `atm` a LEFT JOIN customeraccountdetails cad ON a.customerAccountDetails_id = cad.id WHERE cad.accountNo = ?1", nativeQuery = true)
    ATM findByAccountNumber(String accountNumber);

    @Query(value = "SELECT * FROM `atm` WHERE cardNumber =?1" ,nativeQuery = true)
    ATM findByCardNumber(String cardNumber);
}
