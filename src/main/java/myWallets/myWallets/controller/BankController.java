package myWallets.myWallets.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import myWallets.myWallets.DTO.BankAccountDTO;
import myWallets.myWallets.constant.StatusCode;
import myWallets.myWallets.entity.CustomerAccountDetails;
import myWallets.myWallets.service.CustomerAccountDetailsService;
import myWallets.myWallets.util.BankAccountUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

}
