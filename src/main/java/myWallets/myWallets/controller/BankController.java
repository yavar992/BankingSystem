package myWallets.myWallets.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import myWallets.myWallets.DTO.*;
import myWallets.myWallets.constant.StatusCode;
import myWallets.myWallets.entity.CustomerAccountDetails;
import myWallets.myWallets.service.CustomerAccountDetailsService;
import myWallets.myWallets.util.BankAccountUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/bank")
@Slf4j
public class BankController {

    @Autowired
    private CustomerAccountDetailsService customerAccountDetailsService;

    @Autowired
    private BankAccountUtil bankAccountUtil;

    //OPEN ACCOUNT
    @PostMapping("/openAccount")
    public ResponseEntity<?> openAccount(@RequestParam("UUID") String UUID ,
                                         @Valid @RequestBody BankAccountDTO bankAccountDTO ,
                                         @RequestParam("branchId") Long branchId ){
        try {
            CustomerAccountDetails bankAccount = bankAccountUtil.openAccount(UUID ,bankAccountDTO , branchId);
            if (bankAccount!=null){
                customerAccountDetailsService.saveCustomer(bankAccount);
                return ResponseEntity.status(StatusCode.OK.getCode()).body("Congratulations you have successfully opened you account in happy bank");
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("cannot open the account");
    }

    //GET CUSTOMER INFORMATION
    @GetMapping("/getCustomerInformation")
    public ResponseEntity<?> getCustomerInformation(@RequestParam("UUID")String UUID , @RequestParam("customerId") Long customerId){
        try {
            List<CustomerAccountDetailsDTO> customerAccountDetailsDTOList = customerAccountDetailsService.findCustomerAccountDetails(UUID , customerId);
            if (customerAccountDetailsDTOList!=null && !customerAccountDetailsDTOList.isEmpty()){
                return ResponseEntity.status(HttpStatus.OK).body(customerAccountDetailsDTOList);
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.OK).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("cannot get the customer information");
    }

    //FIND BANK INFORMATION FROM ACCOUNT NO
    @GetMapping("/getAccountInfoByAccountNo")
    public ResponseEntity<?> getBankInformationByAccountNo( @RequestParam("UUID") String UUID,@RequestParam("accountNo") String accountNo){
        try {
            CustomerAccountDetailsDTO customerAllDetails = customerAccountDetailsService.findCustomerByAccountNo(UUID ,accountNo);
            if (customerAllDetails!=null){
                return ResponseEntity.status(HttpStatus.OK).body(customerAllDetails);
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.OK).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("cannot get the customerBankInformation");
    }
    // TRANSFER MONEY FROM ONE ACCOUNT TO ANOTHER ACCOUNT

    
}
