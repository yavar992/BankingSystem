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

    private CustomerAccountDetailsDTO customerAccountDetailsDTO;
    private List<CustomerAccountRecieveDTO> customerAccountDetailsDTOList;
    private BankBranchSendarDTO bankBranchSendarDTO;

}
