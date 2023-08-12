package myWallets.myWallets.DTO;

import lombok.*;

@Data
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionDebitDTO {

    private Double amount;
    private String accountNumber;
    private String ifscCode;
}
