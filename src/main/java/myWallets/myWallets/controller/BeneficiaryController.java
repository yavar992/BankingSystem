package myWallets.myWallets.controller;

import lombok.extern.slf4j.Slf4j;
import myWallets.myWallets.DTO.BeneficiaryDTO;
import myWallets.myWallets.DTO.BeneficiaryVerififyAccountDTO;
import myWallets.myWallets.service.BeneficiaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/beneficiary")
@Slf4j
public class BeneficiaryController {

    @Autowired
    private BeneficiaryService beneficiaryService;

    //REGISTER BENEFICIARY
    @PostMapping("/beneficiaryRegistration")
    public ResponseEntity<?> registerBeneficiary(@RequestParam("UUID") String UUID ,
                                                 @RequestBody BeneficiaryDTO beneficiaryDTO ,
                                                 @RequestParam("bankAccountNumber")String bankAccountNumber){
        try {
            if (beneficiaryService.findAllBeneficiaryAccount()){
                return ResponseEntity.status(HttpStatus.OK).body("You have already added a beneficiary for your account plz update or deleted your beneficiary or add new one");
            }
            if (beneficiaryService.beneficiaryAlreadyExists(beneficiaryDTO.getEmail(), beneficiaryDTO.getPhoneNumber())){
                return ResponseEntity.status(HttpStatus.OK).body("Beneficiary already Exists");
            }
            String beneficiaryRegisterationMessage = beneficiaryService.registerdBeneficiary(UUID ,beneficiaryDTO ,bankAccountNumber);
            if (beneficiaryRegisterationMessage!=null){
                return ResponseEntity.status(HttpStatus.OK).body(beneficiaryRegisterationMessage);
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.OK).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cannot registered the beneficiary account");
    }

    //Validate Beneficiary account
    @PostMapping("/verifyBeneficiaryAccount")
    public ResponseEntity<?> validateBeneficiarAccount(@RequestParam("UUID")String UUID ,
                                                       @RequestParam("bankAccountNumber") String bankAccountNumber ,
                                                       @RequestBody BeneficiaryVerififyAccountDTO beneficiaryVerififyAccountDTO){
        try {
            if (beneficiaryVerififyAccountDTO.getOtp()==null || beneficiaryVerififyAccountDTO.getOtp().length()==0){
                return ResponseEntity.status(HttpStatus.OK).body("plz enter the otp");
            }
            String verifyMessage = beneficiaryService.verifyBeneficiaryAccount(UUID , bankAccountNumber , beneficiaryVerififyAccountDTO);
            if (verifyMessage!=null){
                return ResponseEntity.status(HttpStatus.OK).body(verifyMessage);
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.OK).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("cannot verify the account of the beneficiary");
    }

}
