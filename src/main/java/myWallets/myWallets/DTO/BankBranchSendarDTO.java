package myWallets.myWallets.DTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BankBranchSendarDTO {


    private Long id;

    @NotNull
    @Size(min = 3, max = 50, message = "Invalid Branch Name [3-50 characters only]")
    private String branchName;

    @NotNull
    private String streetAddress;  //happybank002@happybank.org.in

    @NotNull
    private String city;

    @NotNull
    private String state;

    private String  branchPhoneNumber;
}
