package myWallets.myWallets.exceptionHandling;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorMessage {
    private Integer statusCode;
    private String message;
    private String path;
}
