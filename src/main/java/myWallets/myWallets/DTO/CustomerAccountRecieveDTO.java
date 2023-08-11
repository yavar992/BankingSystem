package myWallets.myWallets.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.ZonedDateTime;

@Builder
@Data
public class CustomerAccountRecieveDTO {

    private Long id;

    @NotNull
    @Size(min = 3, message = "Invalid Customer name [ cotains at least 3 characters ]")
    private String customerName;

    @NotNull
    @Size(min = 10, max = 14 ,message = "Invalid Mobile Number [ 9-13 Digit Only ] ")
    private String mobileNumber;

    @NotNull
    @Size(min = 6, max = 50, message = "Invalid Email [ must be 6 to 50 characters ]")
    private String email;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING , pattern = "dd-MM-yyyy")
    private LocalDate dateOfBirth;

    private ZonedDateTime updateDate;


}
