package myWallets.myWallets.controller;

import lombok.extern.slf4j.Slf4j;
import myWallets.myWallets.DTO.TransactionDTO;
import myWallets.myWallets.DTO.TransactionDebitDTO;
import myWallets.myWallets.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/transactions")
@Slf4j
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/creditMoney")
    public ResponseEntity<?> creditMoneyToAccount(@RequestParam("UUID") String UUID , @RequestBody TransactionDTO transactionDTO){
        try {
            String creditMessage = transactionService.creditMoneyToAccount(UUID, transactionDTO);
            if (creditMessage!=null){
                return ResponseEntity.status(HttpStatus.OK).body("Success! Your deposit transaction has been processed successfully");
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.OK).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("cannot credit money to you account");
    }

    //  DEBIT AMOUNT
    @PostMapping("/debitMoney")
    public ResponseEntity<?> debitMoney(@RequestParam("UUID")String UUID , @RequestBody TransactionDTO transactionDebitDTO){
        try {
            String debitMessage = transactionService.debitMoneyFromAccount(UUID ,transactionDebitDTO);
            if (debitMessage!=null){
                return ResponseEntity.status(HttpStatus.OK).body("Success! Your withdrawal transaction has been processed successfully");
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.OK).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("cannot debit the money");
    }

    //TRANSFER MONEY FROM ONE ACCOUNT TO ANOTHER ACCOUNT
    @PostMapping("/transferMoney")
    public ResponseEntity<?> transferMoney(@RequestParam("UUID")String UUID ,
                                           @RequestParam("customerAccountId")String customerAccountId ,
                                           @RequestBody TransactionDTO transactionDTO){
        try {
            String transactionMessage =transactionService.transfarMoney(UUID ,customerAccountId ,transactionDTO);
            if(transactionMessage!=null){
                return ResponseEntity.status(HttpStatus.OK).body("Success! Your money  has been transfer successfully");
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.OK).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Transaction failed to transfer money ");

    }

}
