package myWallets.myWallets.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class CustomerDTO {

    private Long id;

    @NotNull
    @Size(min = 3, message = "Invalid Customer name [ cotains at least 3 characters ]")
    private String customerName;

    @NotNull
    @Size(min = 10, max = 14 ,message = "Invalid Mobile Number [ 9-13 Digit Only ] ")
    private String mobileNumber;

    @NotNull
    @Size(min = 2,max = 3 , message = "country code should be of 2-3 digits")
    private String countryCode;

    @NotNull
    @Size(min = 6, max = 12, message = "Invalid Password [ must be 6 to 8 characters ]")
    private String password;

    @NotNull
    @Size(min = 10, message = "Invalid Address [ must be at least 10 characters ]")
    private String address;

    @NotNull
    @Size(min = 6, max = 50, message = "Invalid Email [ must be 6 to 50 characters ]")
    private String email;

    @NotNull
    private String dateOfBirth;

    @NotNull
    private String gender;
}
