package myWallets.myWallets.repository;

import myWallets.myWallets.entity.Beneficiary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BeneficiaryRepo extends JpaRepository<Beneficiary , Long> {
    @Query(value = " SELECT * FROM `beneficiary` WHERE email=?1 OR phoneNumber =?2" ,nativeQuery = true)
    Beneficiary findByBeneficiaryEmailOrPhoneNumber(String email, String phoneNumber);
}
