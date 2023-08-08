package myWallets.myWallets.DTO;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OptDTO {

    @NotNull
    @Size(min = 4,max = 10 ,message = "msg should be of 10 digits")
    private String otp;

   private String password;
}
