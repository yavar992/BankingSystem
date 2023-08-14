package myWallets.myWallets.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;
import java.time.ZonedDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamicUpdate
@Entity
@Getter
@Setter
@ToString
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
    @JsonFormat(shape = JsonFormat.Shape.STRING , pattern = "dd-MM-yyyy")
    private LocalDate atmExpirationDate;
    private String pin;
    private boolean isVerified;
    private Long atmOtp;

    @ToString.Exclude
    @OneToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    private CustomerAccountDetails customerAccountDetails;


}
