package myWallets.myWallets.DTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BankBranchDTO {

    @NotNull
    @Size(min = 3, max = 50, message = "Invalid Branch Name [3-50 characters only]")
    private String branchName;

    @NotNull
    private String streetAddress;  //happybank002@happybank.org.in

    @NotNull
    private String city;

    @NotNull
    private String state;

    @NotNull
    private String postalCode;

    private String  branchPhoneNumber;

    private String branchEmail;

    private String branchManager;


}
