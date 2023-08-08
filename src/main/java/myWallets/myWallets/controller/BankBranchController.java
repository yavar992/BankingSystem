package myWallets.myWallets.controller;

import com.google.gson.Gson;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import myWallets.myWallets.DTO.BankBranchDTO;
import myWallets.myWallets.constant.StatusCode;
import myWallets.myWallets.entity.BankBranches;
import myWallets.myWallets.service.BankBranchService;
import myWallets.myWallets.util.BankBranchesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/user/bankAccount/branch")
@Slf4j
public class BankBranchController {

    @Autowired
    private BankBranchService bankBranchService;

    @Autowired
    private BankBranchesUtil bankBranchesUtil;

    //ADD BRANCH TO THE BANK
    @PostMapping("/addBranch")
    public ResponseEntity<?> addBranch(@RequestParam("UUID") String UUID , @Valid  @RequestBody BankBranchDTO bankBranchDTO){
        try {
        BankBranches addBranchToBank = bankBranchesUtil.addBranchesToBank(UUID , bankBranchDTO);
        if (addBranchToBank!=null){
            bankBranchService.saveBranchesToBank(addBranchToBank);
            return ResponseEntity.status(HttpStatus.OK).body("Bank Branch added successfully");
        }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("cannot add the branch to the bank ");
    }

    //FIND All BRANCH OF BANK
    @GetMapping("/getAllBranches")
    public ResponseEntity<?> geAllBranchesOfBank(@RequestParam("UUID") String UUID){
        try {
            List<BankBranches> bankBranch = bankBranchService.getAllBranches(UUID);
            if (bankBranch!=null && !bankBranch.isEmpty()){
                return ResponseEntity.status(HttpStatus.OK).body(bankBranch);
            }
        }catch (Exception e){
            log.info(e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Could not find the bank branches ");
    }

    //FIND BANK BRANCHES BY BRANCH NAME
    @GetMapping("/getBranchesByName/{branchName}")
    private ResponseEntity<?> getBranchesByName(@RequestParam("UUID") String UUID , @PathVariable("branchName") String branchName){
        try {
             List<BankBranches> bankBranches = bankBranchService.getBankBranchesByBankBranchName(UUID, branchName);
            if (branchName!=null && !bankBranches.isEmpty()){
                return ResponseEntity.status(HttpStatus.OK).body(bankBranches);
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No branch found for branch  " + branchName);
    }

    //GET THE BANK BRANCH CODE BY THE BRANCH NAME
    @GetMapping("getBranchCode/{branchName}")
    public ResponseEntity<?> getBranchCodeByName(@RequestParam("UUID") String UUID , @PathVariable("branchName") String branchName){
        try {
            Long branchCode = bankBranchService.getBranchCodeByBranchName(branchName , UUID);
            if (branchCode!=null){
                return ResponseEntity.status(HttpStatus.OK).body(branchCode);
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("cannot get the bank branch");
    }

    //ADD BRANCHES TO THE BANK
    @PostMapping("/addBranches/{id}")
    public ResponseEntity<?> addBranch(@RequestParam("UUID") String UUID , @PathVariable("id") Long id){
        return ResponseEntity.status(HttpStatus.OK).body("");
    }

    //GET BANK BRANCH FOR BY THE BRANCH ID
    @GetMapping("getBankByBranchId/{id}")
    private ResponseEntity<?> getBankByBranch(@RequestParam("UUID") String UUID , @PathVariable("id") Long id){
        try {
            BankBranches bankBranches = bankBranchService.getBranchesByBranchId(UUID ,id);
            if (bankBranches!=null){
                return ResponseEntity.status(StatusCode.OK.getCode()).body(bankBranches);
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("cannot get the banch by branch id " + id);
    }

}
