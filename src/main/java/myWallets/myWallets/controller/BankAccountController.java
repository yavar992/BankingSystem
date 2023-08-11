package myWallets.myWallets.controller;

import lombok.extern.slf4j.Slf4j;
import myWallets.myWallets.entity.BankAccount;
import myWallets.myWallets.service.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/users/")
@Slf4j
public class BankAccountController {

    @Autowired
    private BankAccountService bankAccountService;


    @GetMapping("/getAllBank")
    public ResponseEntity<?> getBankName(){
        try {
           List<BankAccount> bankAccount = bankAccountService.findBankAccount();
            if (bankAccount!=null){
                return ResponseEntity.status(HttpStatus.OK).body(bankAccount);
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("cannot get the bank");
    }

    @GetMapping("/allBank")
    public ResponseEntity<?> getAllBank(){
        try {
                List<BankAccount>bankAccounts = bankAccountService.getllBanks();
                if (bankAccounts!=null && !bankAccounts.isEmpty()){
                    return ResponseEntity.status(HttpStatus.OK).body(bankAccounts);
                }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body("cannot get the banks");
    }

    @GetMapping("/getBankById/{id}")
    public ResponseEntity<?> getBankById(@PathVariable("id") Long id){
        try{
            BankAccount bankAccount = bankAccountService.findAccountById(id);
            if (bankAccount!=null){
                return ResponseEntity.status(HttpStatus.OK).body(bankAccount);
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("CANNOT get the bank");
    }
}
