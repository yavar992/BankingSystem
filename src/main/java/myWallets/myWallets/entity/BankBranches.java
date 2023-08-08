package myWallets.myWallets.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;


@Data
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

    @ManyToOne
    @JsonIgnore
    private BankAccount bankAccount;

    @OneToMany(mappedBy = "bankBranches" , fetch = FetchType.LAZY , cascade = CascadeType.ALL , orphanRemoval = true)
    private List<CustomerAccountDetails> customers = new ArrayList<>();




}
