package myWallets.myWallets.entity;

import java.time.LocalDateTime;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CurrentUserSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long CurrentUserSessionId;

    private Long userId;

    private String uuid;

    private LocalDateTime localDateTime;

    @OneToOne
    private Customer customer;

    public CurrentUserSession(Long userId, String uuid, LocalDateTime localDateTime) {
        this.userId = userId;
        this.uuid = uuid;
        this.localDateTime = localDateTime;
    }
}
