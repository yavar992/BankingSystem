package myWallets.myWallets.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@DynamicUpdate
public class BankBranches {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 5, max = 10, message = "Invalid Branch Code [5-10 characters only]")
    private String branchCode;  //886500

    @NotNull
    @Size(min = 3, max = 50, message = "Invalid Branch Name [3-50 characters only]")
    private String branchName;

    @NotNull
    @Size(min = 5, max = 12, message = "Invalid IFSC Code [5-10 Characters only]")
    private String IFSCCode;

    @NotNull
    private String streetAddress;

    @NotNull
    private String city;

    @NotNull
    private String state;

    @NotNull
    private String postalCode;

    private String  branchPhoneNumber;

    private String branchEmail;

    private String branchManager;

    private String openingHours;

    private String closingHours;

    @ToString.Exclude
    @ManyToOne
    @JsonIgnore
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private BankAccount bankAccount;


    @ToString.Exclude
    @OneToMany( fetch = FetchType.LAZY , cascade = CascadeType.ALL , orphanRemoval = true)
    @JsonIgnore
    private List<Customer> customers = new ArrayList<>();

}
