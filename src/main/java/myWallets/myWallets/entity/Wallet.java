package myWallets.myWallets.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Builder
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String walletId; // Generating a unique walletId using UUID

    private Double balance;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customerAccountDetails_id")
    private CustomerAccountDetails owner;



}
