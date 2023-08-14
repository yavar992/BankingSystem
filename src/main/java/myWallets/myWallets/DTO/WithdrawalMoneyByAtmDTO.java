package myWallets.myWallets.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WithdrawalMoneyByAtmDTO {

    @NotNull
    @Size(min = 10 , max = 18 , message = "Invalid CardNumbers [10-18 characters only ]")
    private String cardNumber;
    @NotNull
    @Size(min = 3 , max = 3 , message = "Invalid cvv Number format [should be 3 characters only ]")
    private String cvv;
    @JsonFormat(shape = JsonFormat.Shape.STRING , pattern = "dd-MM-yyyy")
    private LocalDate atmExpirationDate;
    private Double balance;
    private String description;
}
