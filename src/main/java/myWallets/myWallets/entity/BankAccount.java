package myWallets.myWallets.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import myWallets.myWallets.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.ZonedDateTime;
import java.util.List;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
@ToString
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @NotNull
    @Size(min = 3, max = 15,message = "Invalid Bank Name [ 3-15 characters only ]")
    private String bankName;

    @NotNull
    private String bankIdentificationNumber;  //or bankCode = HYBK

    @Column(name = "bankOpeningDate")
    private ZonedDateTime zonedDateTime;

    @NotNull
    private String customerSupportNumber;

    @NotNull
    private String customerSupportEmail;


    @ToString.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "bankAccount" ,fetch = FetchType.LAZY ,cascade = CascadeType.ALL , orphanRemoval = true)
    private List<BankBranches> bankBranches;


}
