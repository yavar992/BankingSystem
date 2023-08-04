package myWallets.myWallets.DTO;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Login {

    @NotNull
    @Size(min = 6, max = 50, message = "Invalid Email [ must be 6 to 50 characters ]")
    private String email;

    @NotNull
    @Size(min = 6, max = 12, message = "Invalid Password [ must be 6 to 8 characters ]")
    private String password;


}
