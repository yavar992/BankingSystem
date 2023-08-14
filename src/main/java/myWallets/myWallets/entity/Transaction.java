package myWallets.myWallets.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import myWallets.myWallets.util.MaskData;

import java.time.LocalDate;

@Entity
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String transactionId;
    private String transactionType;
    @JsonFormat(shape = JsonFormat.Shape.STRING , pattern = "dd-MM-yyyy")
    private LocalDate transactionDate;
    @NotNull
    private Double amount;
    private String Description;
    @NotNull
    @MaskData
    private String accountNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private CustomerAccountDetails customerAccountDetails;

    @PrePersist
    public void preUpdate(){
        transactionType = transactionType.toUpperCase();
    }
}
