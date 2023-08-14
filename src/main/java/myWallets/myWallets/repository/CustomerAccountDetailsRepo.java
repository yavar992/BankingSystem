package myWallets.myWallets.repository;

import myWallets.myWallets.entity.BankAccountType;
import myWallets.myWallets.entity.CustomerAccountDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CustomerAccountDetailsRepo extends JpaRepository<CustomerAccountDetails , Long> {
    @Query(value = "SELECT * FROM `customeraccountdetails` WHERE bankAccountType =?1" ,nativeQuery = true)
    CustomerAccountDetails findByAccountType(String bankAccountType);

    @Query(value = "SELECT * FROM `customeraccountdetails` WHERE customer_id =?1" , nativeQuery = true)
   Optional<CustomerAccountDetails> findByCustomerId(Long customerId);

    @Query(value = "SELECT * FROM customeraccountdetails WHERE accountNo=?1" ,nativeQuery = true)
    CustomerAccountDetails findByAccountNumber(String accountNo);

    @Query(value = "SELECT a.cardNumber FROM `customeraccountdetails` cad LEFT JOIN atm a ON cad.id = a.customerAccountDetails_id WHERE cad.accountNo =?1",nativeQuery = true)
    String findAtmByAccountNumber(String accountNumber);

    @Query(value = "SELECT * FROM `customeraccountdetails` WHERE customer_id =?1" , nativeQuery = true)
    List<CustomerAccountDetails> findAllAccountByCustomerId(Long customerId);

    @Query(value = "SELECT cad.id , cad.Status ,cad.accountCloseDate , cad.accountHolderName , cad.accountNo ,cad.accountOpeningDate , cad.bankAccountType , cad.balance ,cad.currency, cad.atm_id ,\n" +
            "cad.bankAccount_id , cad.customer_id FROM `customer` c LEFT JOIN customeraccountdetails cad ON c.id =cad.customer_id LEFT JOIN atm a ON cad.id =a.customerAccountDetails_id WHERE a.cardNumber = ?1" ,nativeQuery = true)
    CustomerAccountDetails findByCustomerATMNumber(String atmNumber);
}
