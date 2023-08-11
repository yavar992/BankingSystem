package myWallets.myWallets.util;

import lombok.extern.slf4j.Slf4j;
import myWallets.myWallets.DTO.BankBranchDTO;
import myWallets.myWallets.DTO.BankBranchSendarDTO;
import myWallets.myWallets.constant.HappyBankUtilMethods;
import myWallets.myWallets.convertor.BankBranchConvertor;
import myWallets.myWallets.entity.BankAccount;
import myWallets.myWallets.entity.BankBranches;
import myWallets.myWallets.entity.CurrentUserSession;
import myWallets.myWallets.exceptionHandling.BankBranchesNotFoundException;
import myWallets.myWallets.exceptionHandling.BankNotFoundException;
import myWallets.myWallets.exceptionHandling.UnverifiedCustomerException;
import myWallets.myWallets.repository.BankAccountRepo;
import myWallets.myWallets.repository.BankBranchRepo;
import myWallets.myWallets.repository.CurrentUserSessionRepo;
import myWallets.myWallets.service.BankBranchService;
import myWallets.myWallets.service.CustomerService;
import myWallets.myWallets.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class BankBranchesUtilImpl implements BankBranchesUtil{

    @Autowired
    BankAccountRepo bankAccountRepo;

    @Autowired
    CurrentUserSessionRepo currentUserSessionRepo;

    @Autowired
    CustomerService customerService;

    @Autowired
    HappyBankUtilMethods happyBankUtilMethods;
    @Autowired
    BankBranchRepo bankBranchRepo;

    @Override
    public BankBranches addBranchesToBank(String uuid, BankBranchDTO bankBranchDTO) {
        try {
            happyBankUtilMethods.authorizeAndGetVerifiedCustomer(uuid);
            List<BankAccount>bankAccounts = bankAccountRepo.findAll();
            happyBankUtilMethods.validateHappyBank(bankAccounts);
            BankAccount bankAccount = bankAccounts.get(0);
            String branchCode = String.valueOf(Validator.otp());
            BankBranches bankBranches =  BankBranchConvertor.convertBankBranchDtoToBankBranches(bankBranchDTO);
            bankBranches.setBankAccount(bankAccount);
            bankBranches.setBranchCode(branchCode);
            bankBranches.setIFSCCode("HYBK0"+branchCode);
            bankBranches.setClosingHours("5:00 PM");
            bankBranches.setOpeningHours("9:00 AM");
            return bankBranches;
        }catch (Exception e){
            throw e;
        }
    }

    @Override
    public BankBranches updateBankBranch(String uuid, Long id, BankBranchSendarDTO bankBranchSendarDTO) {
        try {
            happyBankUtilMethods.authorizeAndGetVerifiedCustomer(uuid);
            BankBranches bankBranches = bankBranchRepo.findById(id).orElseThrow(()->new BankBranchesNotFoundException("Branch Not Found for id " + id));
            bankBranches.setBranchName(bankBranchSendarDTO.getBranchName().toUpperCase());
            bankBranches.setBranchPhoneNumber(bankBranchSendarDTO.getBranchPhoneNumber().toUpperCase());
            bankBranches.setCity(bankBranchSendarDTO.getCity().toUpperCase());
            bankBranches.setState(bankBranchSendarDTO.getState().toUpperCase());
            bankBranches.setStreetAddress(bankBranchSendarDTO.getStreetAddress().toUpperCase());
            return bankBranches;
        }catch (Exception e){
            throw e;
        }
    }
}


// BankBranches bankBranches = BankBranches.builder()
//                    .bankAccount(bankAccount)
//                    .branchCode(branchCode)
//                    .branchEmail(bankBranchDTO.getBranchEmail())
//                    .branchManager(bankBranchDTO.getBranchManager().toUpperCase())
//                    .branchName(bankBranchDTO.getBranchName().toUpperCase())
//                    .branchPhoneNumber(bankBranchDTO.getBranchPhoneNumber())
//                    .closingHours("5:00 PM")
//                    .state(bankBranchDTO.getState().toUpperCase())
//                    .city(bankBranchDTO.getCity().toUpperCase())
//                    .IFSCCode("HYBK0"+branchCode)
//                    .openingHours("9:00 AM")
//                    .postalCode(bankBranchDTO.getPostalCode())
//                    .streetAddress(bankBranchDTO.getStreetAddress().toUpperCase())
//                    .build();