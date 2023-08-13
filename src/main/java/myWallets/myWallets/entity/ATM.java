package myWallets.myWallets.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;
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
    @Size(min = 10 , max = 18 , message = "Invalid CardNumbers [10-18 characters only ]")
    private String cardNumber;

    @NotNull
    @Size(min = 3 , max = 3 , message = "Invalid cvv Number format [should be 3 characters only ]")
    private String cvv;

//    @JsonFormat(shape = JsonFormat.Shape.STRING , pattern = "dd-MM-yyyy")
    private LocalDate atmIssueAt;
    private LocalDate atmExpirationDate;
    private String pin;
    private boolean isVerified;
    private Long atmOtp;

    @OneToOne(cascade = CascadeType.ALL)
    private CustomerAccountDetails customerAccountDetails;

}
