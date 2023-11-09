package myWallets.myWallets.DTO;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerAllDetails {

    private CustomerAccDTO customerAccDTO;
     private BankBranchSendarDTO bankBranchSendarDTO;
     private List<CustomerAccountDetailsDTO> customerAccountDetailsDTO;


}
