package myWallets.myWallets.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CustomerAccountDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 10 , max = 16 , message = "Invalid accountNo [should be of 13-14 characters  ]")
    private String accountNo;
    //HYBK -- is the account identifier for my HappyBank
    //next four digits will be bank branch that will be associated to the customer

    @NotNull
    @Size(min = 5,max = 30 ,message = "Invalid customerName [5-30 Characters only]")
    private String accountHolderName;

    @Enumerated(EnumType.STRING)
    private BankAccountType bankAccountType;

    @NotNull
    private Double balance;

    @NotNull
    @Size(min = 2 , max = 10 ,message = "Invalid Currency [2-10 characters only ]")
    private String currency;

    @NotNull
    private String Status;

    private ZonedDateTime accountOpeningDate;

    private ZonedDateTime accountCloseDate;

    @ManyToOne(fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id")
    Customer customer;

    @OneToOne(fetch = FetchType.LAZY , orphanRemoval = true)
    @JoinColumn(name = "atm_id")
    ATM atm;

    @ManyToOne(fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    BankAccount bankAccount;

    @OneToMany( mappedBy = "customerAccountDetails" , fetch = FetchType.LAZY , cascade = CascadeType.ALL ,orphanRemoval = true)
    List<Transaction> transactions;

    public void saveCustomer(){
        
    }
}
