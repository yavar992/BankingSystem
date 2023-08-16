package myWallets.myWallets.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BeneficiaryDTO {

    @NotNull
    private String name; // Beneficiary's full name
    @NotNull
    private String relationship; // Relationship with the account holder
    @NotNull
    private String address; // Beneficiary's address
    @NotNull
    private String phoneNumber; // Beneficiary's contact number
    @NotNull
    @Email
    private String email; // Beneficiary's em
    private LocalDate beneficiaryAddedTime; // Date when beneficiary's details are added

}
