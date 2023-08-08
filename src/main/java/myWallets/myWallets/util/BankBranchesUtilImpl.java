package myWallets.myWallets.util;

import lombok.extern.slf4j.Slf4j;
import myWallets.myWallets.DTO.BankBranchDTO;
import myWallets.myWallets.entity.BankAccount;
import myWallets.myWallets.entity.BankBranches;
import myWallets.myWallets.entity.CurrentUserSession;
import myWallets.myWallets.exceptionHandling.BankNotFoundException;
import myWallets.myWallets.exceptionHandling.UnverifiedCustomerException;
import myWallets.myWallets.repository.BankAccountRepo;
import myWallets.myWallets.repository.CurrentUserSessionRepo;
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

    @Override
    public BankBranches addBranchesToBank(String uuid, BankBranchDTO bankBranchDTO) {
        try {

            Boolean isVerified = customerService.checkIfaUserIsVerifiedOrNot(uuid);
            if (isVerified==false){
                throw new UnverifiedCustomerException("your account is not verified yet plz verify your account");
            }
            Optional<CurrentUserSession> currentUserSession = currentUserSessionRepo.findByUUID(uuid);
            if (currentUserSession.isPresent()){
                throw new myWallets.myWallets.exceptionHandling.LoginException("User is already logged in");
            }
            List<BankAccount>bankAccounts = bankAccountRepo.findAll();
            if (bankAccounts==null || bankAccounts.isEmpty()){
                throw new BankNotFoundException("Bank Not Found");
            }
            BankAccount bankAccount = bankAccounts.get(0);
            String branchCode = String.valueOf(Validator.otp());
            BankBranches bankBranches = BankBranches.builder()
                    .bankAccount(bankAccount)
                    .branchCode(branchCode)
                    .branchEmail(bankBranchDTO.getBranchEmail())
                    .branchManager(bankBranchDTO.getBranchManager().toUpperCase())
                    .branchName(bankBranchDTO.getBranchName().toUpperCase())
                    .branchPhoneNumber(bankBranchDTO.getBranchPhoneNumber())
                    .closingHours("5:00 PM")
                    .state(bankBranchDTO.getState().toUpperCase())
                    .city(bankBranchDTO.getCity().toUpperCase())
                    .IFSCCode("HYBK0"+branchCode)
                    .openingHours("9:00 AM")
                    .postalCode(bankBranchDTO.getPostalCode())
                    .streetAddress(bankBranchDTO.getStreetAddress().toUpperCase())
                    .build();
            return bankBranches;
        }catch (Exception e){
            throw e;
        }
    }
}
