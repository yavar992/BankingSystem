package myWallets.myWallets.repository;

import lombok.CustomLog;
import myWallets.myWallets.entity.CurrentUserSession;
import myWallets.myWallets.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface CustomerRepo extends JpaRepository<Customer ,Long> {

    @Query(value = "SELECT * FROM customer WHERE otp =?1" ,nativeQuery = true)
    Optional<Customer> findByOtp(String otp);


    Customer findByEmail(String email);

    @Query(value = "SELECT * FROM customer WHERE mobileNumber =?1" ,nativeQuery = true)
    Customer findByMobileNumber(String mobileNumber);


    @Query(value = "SELECT * FROM `customer` c LEFT JOIN currentusersession cs ON c.id = cs.userId WHERE cs.uuid=?1",nativeQuery = true)
    Optional<Customer> findByUUID(String uuid);

    @Query(value = "SELECT * FROM customer WHERE mobileNumber = ?1 OR email=?2" ,nativeQuery = true)
    Customer findByMobileNumberOrEmail(String mobileNumber, String email);
}
