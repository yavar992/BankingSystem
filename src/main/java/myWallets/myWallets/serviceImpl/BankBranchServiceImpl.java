package myWallets.myWallets.serviceImpl;

import lombok.extern.slf4j.Slf4j;
import myWallets.myWallets.DTO.BankBranchSendarDTO;
import myWallets.myWallets.constant.HappyBankUtilMethods;
import myWallets.myWallets.constant.StatusCode;
import myWallets.myWallets.convertor.BankBranchConvertor;
import myWallets.myWallets.entity.BankBranches;
import myWallets.myWallets.entity.CurrentUserSession;
import myWallets.myWallets.exceptionHandling.BankBranchesNotFoundException;
import myWallets.myWallets.repository.BankAccountRepo;
import myWallets.myWallets.repository.BankBranchRepo;
import myWallets.myWallets.repository.CurrentUserSessionRepo;
import myWallets.myWallets.repository.CustomerRepo;
import myWallets.myWallets.service.BankBranchService;
import myWallets.myWallets.validator.ValidatorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.security.auth.login.LoginException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
public class BankBranchServiceImpl  implements BankBranchService {

    @Autowired
    private BankBranchRepo bankBranchRepo;

    @Autowired
    BankAccountRepo bankAccountRepo;

    @Autowired
    HappyBankUtilMethods happyBankUtilMethods;

    @Autowired
    CurrentUserSessionRepo currentUserSessionRepo;

    @Autowired
    CustomerRepo customerRepo;

    @Override
    public List<BankBranches> getAllBranches(String uuid) throws LoginException {
        try {
            Optional<CurrentUserSession> currentUserSession = currentUserSessionRepo.findByUUID(uuid);
            if (!currentUserSession.isPresent()){
                throw new LoginException("User Not found in current user session for uuid " + uuid);
            }

            List<BankBranches> bankBranches = bankBranchRepo.findAll();
            log.info("Branches: " + bankBranches);
            if (bankBranches==null || bankBranches.isEmpty()){
                throw new BankBranchesNotFoundException("No Bank branches found ");
            }
            return bankBranches;
        }catch (Exception e){
            log.info("can't get branches from bank due to " + e.getMessage());
            throw e;
        }
    }

    @Override
    public List<BankBranches> getBankBranchesByBankBranchName(String uuid, String branchName)  {
        try {
            Optional<CurrentUserSession> currentUserSession = currentUserSessionRepo.findByUUID(uuid);
            if (!currentUserSession.isPresent()) {
                throw new myWallets.myWallets.exceptionHandling.LoginException("User not found for uuid " + uuid);
            }
            List<BankBranches> bankBranches = bankBranchRepo.findByName(branchName);
            log.info("Bank branches " + bankBranches);
            log.info("Bank branch " + bankBranches.get(0).getBranchCode());
            if (bankBranches==null || bankBranches.isEmpty()){
                throw new BankBranchesNotFoundException("Bank branch not found for branch " + branchName);
            }
            return bankBranches;
        }catch (Exception e){
            throw e;
        }
    }

    @Override
    public Long getBranchCodeByBranchName(String branchName, String uuid)  {
        try{
            happyBankUtilMethods.authorizeAndGetVerifiedCustomer(uuid);
            List<BankBranches> bankBranches = getBankBranchesByBankBranchName(branchName ,uuid);
            BankBranches bankBranches1 = bankBranches.get(0);
            log.info("bankBranches :" + bankBranches1 );
            Long bankBrancheCode = Long.valueOf(bankBranches1.getBranchCode());
            return bankBrancheCode;
        }catch (Exception e){
            throw e;
        }
    }

    @Override
    public void saveBranchesToBank(BankBranches addBranchToBank) {
            bankBranchRepo.saveAndFlush(addBranchToBank);
    }

    @Override
    public BankBranchSendarDTO getBranchesByBranchId(String uuid, Long id) {
        try {
            happyBankUtilMethods.authorizeAndGetVerifiedCustomer(uuid);
            Optional<BankBranches> bankBranches = bankBranchRepo.findById(id);
            if (bankBranchRepo==null || bankBranches.isEmpty()){
                throw new BankBranchesNotFoundException(StatusCode.BRANCH_NOT_EXIST.getMessage() + id);
            }
            BankBranches bankBranches1 = bankBranches.get();
            return BankBranchConvertor.convertBankBranchToBankBranchDTO(bankBranches1);
        }catch (Exception e){
            throw e;
        }
    }

    @Override
    public BankBranches bankBranchesById(Long id) {
        try {
            Optional<BankBranches> bankBranches = bankBranchRepo.findById(id);
            ValidatorUtils.validateBankBranch(bankBranches);
            BankBranches bankBranches1 = bankBranches.get();
            return bankBranches1;
        }catch (Exception e){
            throw e;
        }
    }

    @Override
    public List<BankBranchSendarDTO> getALLbranchesData(String uuid) {
        happyBankUtilMethods.authorizeAndGetVerifiedCustomer(uuid);
        List<BankBranches> bankBranches = bankBranchRepo.findAll();
        if (bankBranches==null){
            throw new BankBranchesNotFoundException("No branches found");
        }
        return bankBranches.stream().map(BankBranchConvertor::convertBankBranchToBankBranchDTO).collect(Collectors.toList());
    }

    @Override
    public BankBranchSendarDTO findBankBranchById(String uuid, Long id) {
        return null;
    }

    @Override
    public void updateBankBranch(BankBranches updateMessage) {
        bankBranchRepo.saveAndFlush(updateMessage);
    }

    @Override
    public boolean branchAlreadyExist(String branchEmail, String branchPhoneNumber) {
        try {
            BankBranches bankBranches =bankBranchRepo.findByBankBranchCode(branchEmail ,branchPhoneNumber);
            return bankBranches!=null;
        }catch (Exception e){
            throw e;
        }
    }
}
