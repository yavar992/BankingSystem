package myWallets.myWallets.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.time.ZonedDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamicUpdate
@Entity
@Data
public class ATM {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3, max = 40 , message = "Invalid name [3-40 characters only]")
    private String customerNameOnATM;
    @NotNull
    @Size(min = 10 , max = 15 , message = "Invalid CardNumbers [10-15 characters only ]")
    private String cardNumber;

    @NotNull
    @Size(min = 3 , max = 3 , message = "Invalid cvv Number format [should be 3 characters only ]")
    private String cvv;

    private ZonedDateTime atmIssueAt;
    private ZonedDateTime atmExpirationDate;

    @OneToOne
    private BankAccount bankAccount;
}
