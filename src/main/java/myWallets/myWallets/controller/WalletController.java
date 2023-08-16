package myWallets.myWallets.controller;

import myWallets.myWallets.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/wallet")
public class WalletController {

    @Autowired
    private WalletService walletService;

    //OPEN WALLET FOR CUSTOMER
    @PostMapping("/openWallet")
    public ResponseEntity<?> openWalletAccount(@RequestParam("UUID")String UUID , @RequestParam("accountNumber") String accountNumber){
        try {
            if (walletService.walletAlreadyExists()){
                return ResponseEntity.status(HttpStatus.OK).body("Wallet already exists");
            }
            String walletOpenMessage = walletService.openWallet(UUID , accountNumber);
            if (walletOpenMessage!=null){
                return ResponseEntity.status(HttpStatus.OK).body(walletOpenMessage);
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.OK).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cannot open the wallet for the customer account " + accountNumber);
    }

}
