package myWallets.myWallets.DTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import myWallets.myWallets.entity.BankAccountType;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class BankAccountDTO {

    @NotNull
    @Size(min = 5,max = 30 ,message = "Invalid customerName [5-30 Characters only]")
    private String accountHolderName;

    private BankAccountType bankAccountType;
    @NotNull
    private Double balance;

    private Long branchId;

    private Long bankId;


}
