package myWallets.myWallets.entity;

import java.time.LocalDateTime;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CurrentUserSession {

    @Id
    @Column(unique = true)
    private Long userId;

    private String uuid;

    private LocalDateTime localDateTime;
}
