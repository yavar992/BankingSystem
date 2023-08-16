package myWallets.myWallets.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Beneficiary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name; // Beneficiary's full name
    private String relationship; // Relationship with the account holder
    private String address; // Beneficiary's address
    private String phoneNumber; // Beneficiary's contact number
    private String email; // Beneficiary's email address
    private boolean allowedToWithdraw; // Flag indicating if beneficiary is allowed to withdraw
    private String authorizationDocument; // Document showing beneficiary's authorization
    private String otp;
    private boolean isAccountVerified; // Flag indicating if the beneficiary account is authorized or not
    private LocalDate beneficiaryAddedTime; // Date when beneficiary's details are added
    private LocalDate beneficiaryAccountUpdatedTime; // Date when


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customerAccountDetails_id")
    private CustomerAccountDetails account;

    @PrePersist
    public void saveData(){
        name = name.toUpperCase();
        relationship = relationship.toUpperCase();
        email = email.toUpperCase();
    }
}
