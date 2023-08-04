package myWallets.myWallets.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.ZonedDateTime;
import java.util.List;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long accountNo;

    @NotNull
    @Size(min = 5,max = 30 ,message = "Invalid customerName [5-30 Characters only]")
    private String accountHolderName;

    @NotNull
    @Size(min = 5, max = 20 ,message = "Invalid AccountType [5-20 character only ]")
    private String accountType;

    @NotNull
    @Size(min = 5, max = 10,message = "Invalid IFSC Code [ 5-10 Characters only ]")
    private String IFSCCode;


    @NotNull
    @Size(min = 3, max = 15,message = "Invalid Bank Name [ 3-15 characters only ]")
    private String bankName;

    @NotNull
    private Double balance;

    @NotNull
    @Size(min = 2 , max = 10 ,message = "Invalid Currency [2-10 characters only ]")
    private String currency;

    @NotNull
    private String Status;

    private ZonedDateTime accountOpeningDate;

    private ZonedDateTime accountCloseDate;

    @NotNull
    @Size(min = 10 , max = 15 , message = "Invalid CardNumbers [10-15 characters only ]")
    private String cardNumber;

    @NotNull
    @Size(min = 3 , max = 3 , message = "Invalid cvv Number format [should be 3 characters only ]")
    private String cvv;


    private ZonedDateTime cardExpirationDate;

    @ManyToOne
    private Customer customer;

    @OneToMany(mappedBy = "bankAccount" ,fetch = FetchType.LAZY ,cascade = CascadeType.ALL)
    private List<BankBranches> bankBranches;
}
