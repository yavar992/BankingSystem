package myWallets.myWallets.repository;

import myWallets.myWallets.entity.CurrentUserSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CurrentUserSessionRepo extends JpaRepository<CurrentUserSession, Long> {
    @Query(value = "SELECT * FROM currentusersession WHERE userId = ?1" ,nativeQuery = true)
    Optional<CurrentUserSession> findByUserId(Long id);

    @Query(value = "SELECT * FROM currentusersession WHERE uuid =?1",nativeQuery = true)
    Optional<CurrentUserSession> findByUUID(String uuid);}
