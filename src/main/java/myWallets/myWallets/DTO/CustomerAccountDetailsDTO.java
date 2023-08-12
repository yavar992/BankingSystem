package myWallets.myWallets.DTO;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import myWallets.myWallets.entity.BankAccountType;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerAccountDetailsDTO {

    private String accountNo;

    private String accountHolderName;

    private BankAccountType bankAccountType;

    private Double balance;
}
