package myWallets.myWallets.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import myWallets.myWallets.DTO.ActivateAccountDTO;
import myWallets.myWallets.entity.CustomerAccountDetails;
import myWallets.myWallets.service.AtmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/customersDetails")
@Slf4j
public class AtmController {

    @Autowired
    private AtmService atmService;

    @PostMapping("/getATM")
    public ResponseEntity<?> getAtm(@RequestParam("UUID") String UUID ,@RequestParam("accountNumber") String accountNumber){
        try {

        String generateAtm = atmService.getAtm(UUID, accountNumber);
        if (generateAtm!=null) {
            return ResponseEntity.status(HttpStatus.OK).body("you have applied for atm successfully");
        }
        }catch (Exception e){
            log.info("exception " + e.getStackTrace().toString());
            log.info(e.getMessage());
            return ResponseEntity.status(HttpStatus.OK).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body("cannot make the atm ");
    }

    //check for the recursive function
    @GetMapping("/checkNullValues")
    public ResponseEntity<?> checkIfMethodIsInRecursive(@RequestParam("accountNumber") String accountNumber){
        try {
        if (atmService.atmAlreadyExistsOrNope(accountNumber)){
            return ResponseEntity.status(HttpStatus.OK).body("you have already availed atm");
        }
            return ResponseEntity.status(HttpStatus.OK).body("you don't have atm");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.OK).body(e.getMessage());
        }
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("cannot get the null values");
    }

    //ACTIVATE ACCOUNT
    @PostMapping("/activateATM")
    public ResponseEntity<?> activateAccount(@RequestParam("UUID")String UUID ,
                                             @RequestBody ActivateAccountDTO activateAccountDTO
            , @RequestParam("accountNumber") String accountNumber
            , @Valid BindingResult bindingResult
                                             ){
        try {
            Map<String, String> validationRequest = new HashMap<>();
            if (bindingResult.hasErrors()) {
                for (FieldError fieldError : bindingResult.getFieldErrors()) {
                    validationRequest.put(fieldError.getField(), fieldError.getDefaultMessage());
                }
                return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(validationRequest.toString());
            }
            String activationMessage = atmService.activateATM(UUID ,activateAccountDTO , accountNumber);
            if (activationMessage!=null){
                return ResponseEntity.status(HttpStatus.OK).body("Congratulation , You have successfully activated your ATM");
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.OK).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cannot activate the your ATM due to some internal error");
    }


    //GET ACCOUNT DETAILS BY THE ACCOUNT NUMBER
    @GetMapping("/getAccountDetails")
    private ResponseEntity<?> getDetails(@RequestParam("UUID") String UUID , @RequestParam("accountNumber") String accountNumber){
        try {
            CustomerAccountDetails customerAccountDetails = atmService.getCustomerAccountDetails(UUID ,accountNumber);
            if (customerAccountDetails!=null){
                return ResponseEntity.status(HttpStatus.OK).body(customerAccountDetails);
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.OK).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("");
    }

    //GENERATE PIN
    @PostMapping("/generatePin")
    public ResponseEntity<?> genrateAtmPIN(@Valid @RequestParam("UUID")String UUID ,
                                           @RequestParam("accountNumber")String accountNumber ,
                                           @RequestBody ActivateAccountDTO activateAccountDTO ){
        try {
            String pinGenerationMessage = atmService.generatePin(UUID ,accountNumber ,activateAccountDTO);
            if (pinGenerationMessage!=null){
                return ResponseEntity.status(HttpStatus.OK).body("Congratulations , you have successfully generate your ATM pin");
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.OK).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("cannot generate PIN");
    }


}
