package myWallets.myWallets.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerAllDetails {

    private CustomerAccountRecieveDTO customerAccountDetailsDTOList;
    private BankBranchSendarDTO bankBranchSendarDTO;
    private List<CustomerAccountDetailsDTO> customerAccountDetailsDTO;


}
