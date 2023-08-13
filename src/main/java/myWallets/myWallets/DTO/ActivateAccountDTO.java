package myWallets.myWallets.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ActivateAccountDTO {

    @NotNull
    @NotBlank
    @Size(min = 4 , max = 4 , message = "otp should be of 4 character")
    private Long otp;
    @NotNull
    @NotBlank
    @Size(min = 3 , max = 3 , message = "cvv should be of  character")
    private String cvv;

    @NotNull
    private String pin;



}
