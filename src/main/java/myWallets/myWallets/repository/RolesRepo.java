package myWallets.myWallets.repository;

import myWallets.myWallets.entity.Roles;
import org.hibernate.type.descriptor.converter.spi.JpaAttributeConverter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RolesRepo extends JpaRepository<Roles , Long> {
    @Override
    Optional<Roles> findById(Long aLong);

    @Query(value = "SELECT * FROM `roles` WHERE roles=?1",nativeQuery = true)
    Optional<Roles> findByName(String name);
}
