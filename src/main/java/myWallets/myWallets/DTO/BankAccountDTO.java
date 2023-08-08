package myWallets.myWallets.DTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class BankAccountDTO {

    @NotNull
    @Size(min = 5,max = 30 ,message = "Invalid customerName [5-30 Characters only]")
    private String accountHolderName;

    @NotNull
    @Size(min = 5, max = 20 ,message = "Invalid AccountType [5-20 character only ]")
    private String accountType;

    @NotNull
    private Double balance;

    private Long branchId;

    private Long bankId;

}
