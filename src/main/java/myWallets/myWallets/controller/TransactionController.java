package myWallets.myWallets.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import myWallets.myWallets.DTO.TransactionDTO;
import myWallets.myWallets.DTO.WithdrawalMoneyByAtmDTO;
import myWallets.myWallets.entity.CustomerAccountDetails;
import myWallets.myWallets.entity.Transaction;
import myWallets.myWallets.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

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

    //WITHDRAWAL MONEY FROM THE ATM
    @PostMapping("/debitByATM")
    public ResponseEntity<?> withdrawMoneyByATM(@Valid @RequestParam("UUID") String UUID ,
                                                @RequestBody WithdrawalMoneyByAtmDTO withdrawalMoneyByAtmDTO){
        try {
            String withdrawalMessage = transactionService.makePaymentByATM(UUID ,withdrawalMoneyByAtmDTO);
            if (withdrawalMessage!=null){
                return ResponseEntity.status(HttpStatus.OK).body("Transaction success");
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.OK).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("cannot make the payment via ATM");
    }

    //GET ACCOUNT DETAILS BY THE CARD NUMBER
    @PostMapping("/getAccountDetailsByCardNumber")
    private ResponseEntity<?> getAccountDetailsByCardNumber(@RequestParam("cardNumber") String cardNumber){
        try {
            CustomerAccountDetails cardAccountNumber = transactionService.getAccountDetailsByCardNumber(cardNumber);
            if (cardAccountNumber!=null){
                return ResponseEntity.status(HttpStatus.OK).body(cardAccountNumber);
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.OK).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("cannot get the customer account details by card Number");
    }

    @GetMapping("/getAllTransaction")
    public ResponseEntity<?> getAllTransaction(){
        try {
            List<Transaction> transactions = transactionService.getAllTransaction();
            if (transactions!=null && !transactions.isEmpty()){
                return ResponseEntity.status(HttpStatus.OK).body(transactions);
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.OK).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("cannot get the transaction details ");
    }

    //GET TRANSACTION BY ACCOUNT NUMBER
    @GetMapping("/getTransactionByAccountNumber")
    private ResponseEntity<?> getTransactionByAccountNumber(@RequestParam("UUID") String UUID , @RequestParam("accountNumber") String accountNumber){
        try {
            List<Transaction> transactions = transactionService.getTransactionsByAccountNumber(UUID ,accountNumber);
            if (transactions!=null && !transactions.isEmpty()){
                return ResponseEntity.status(HttpStatus.OK).body(transactions);
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.OK).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cannot get transaction by account number");
    }

    //GET All Transactions Between Dates
    @PostMapping("/getTransactionBetweenDate")
    private ResponseEntity<?> getTransactionBetweenDate(@RequestParam("UUID") String UUID
            , @RequestParam("accountNumber") String accountNumber , @RequestParam("startingDate") LocalDate startingDate ,
               @RequestParam("endDate") LocalDate endDate){
        try {
            List<Transaction> transactions = transactionService.getTransactionBetweenDates(UUID , accountNumber , startingDate ,endDate);
            if (transactions!=null && !transactions.isEmpty()){
                return ResponseEntity.status(HttpStatus.OK).body(transactions);
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.OK).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cannot get transaction between dates");
    }

    //WITHDRAWAL MONEY BY THE BENEFICIARY
    @PostMapping("/debitMoneyByBeneficiary")
    public ResponseEntity<?> debitMoneyByBeneficiary(@RequestParam("UUID") String UUID ,
                                                     @RequestBody TransactionDTO beneficiaryTransactionDTO){
        try {
            String debitTransactionMessage = transactionService.debitMoneyByBeneficiary(UUID ,  beneficiaryTransactionDTO);
            if (debitTransactionMessage!=null){
                return ResponseEntity.status(HttpStatus.OK).body(debitTransactionMessage);
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.OK).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cannot withdrawal money ");
    }

    //WALLET SECTION
    //transfer money to wallet from bankAccount
    @PostMapping("/addedMoneyToWallet")
    public ResponseEntity<?>transferMoneyToWallet(@RequestParam("UUID") String UUID , @RequestBody TransactionDTO transactionDTO){
        try {
            String moneyTransferMessage = transactionService.addMoneyToWallet(UUID , transactionDTO);
            if (moneyTransferMessage!=null){
                return ResponseEntity.status(HttpStatus.OK).body("Success ! balance added to wallet");
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.OK).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cannot transfer money from bank account to wallet");
    }

    //trasnfer money to bank account from the wallet
    @PostMapping("/transferFromWallet")
    public ResponseEntity<?> transferMoneyFromWalletToBankAccount(@RequestParam("UUID") String UUID ,
                                                                  @RequestParam("recieverAccountNumber") String recieverAccountNumber ,
                                                                  @RequestBody TransactionDTO transactionDTO){
        try {
            String transactionMessage = transactionService.transferMoneyToBankFromWallet(UUID ,recieverAccountNumber , transactionDTO);
            if (transactionMessage!=null){
                return ResponseEntity.status(HttpStatus.OK).body("Transaction successful ");
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.OK).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cannot transfer money from wallet");
    }


    //MAKE PAYMENT BY WALLET
    @PostMapping("/makePaymentByWallet")
    public ResponseEntity<?> makePaymentByWallet(@RequestParam("UUID") String UUID , @RequestBody TransactionDTO transactionDTO){
        try {
            String transactionMessage = transactionService.makePaymentByWallet(UUID , transactionDTO);
            if (transactionMessage!=null){
                return ResponseEntity.status(HttpStatus.OK).body("Payment Successful");
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.OK).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cannot make payment by wallet");
    }

}
