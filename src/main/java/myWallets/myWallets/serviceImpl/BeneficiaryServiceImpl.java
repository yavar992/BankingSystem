package myWallets.myWallets.serviceImpl;

import lombok.extern.slf4j.Slf4j;
import myWallets.myWallets.DTO.BeneficiaryDTO;
import myWallets.myWallets.DTO.BeneficiaryVerififyAccountDTO;
import myWallets.myWallets.constant.HappyBankUtilMethods;
import myWallets.myWallets.convertor.BeneficiaryConverter;
import myWallets.myWallets.entity.Beneficiary;
import myWallets.myWallets.entity.CustomerAccountDetails;
import myWallets.myWallets.event.BeneficiaryEvent;
import myWallets.myWallets.exceptionHandling.BeneficiaryException;
import myWallets.myWallets.exceptionHandling.CustomerAccountException;
import myWallets.myWallets.repository.BeneficiaryRepo;
import myWallets.myWallets.repository.CustomerAccountDetailsRepo;
import myWallets.myWallets.service.BeneficiaryService;
import myWallets.myWallets.service.CustomerAccountDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@Transactional
public class BeneficiaryServiceImpl implements BeneficiaryService {

    @Autowired
    private BeneficiaryRepo beneficiaryRepo;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;
    @Autowired
    CustomerAccountDetailsRepo customerAccountDetailsRepo;

    @Autowired
    HappyBankUtilMethods happyBankUtilMethods;
    @Override
    public String registerdBeneficiary(String uuid, BeneficiaryDTO beneficiaryDTO, String bankAccountNumber) {
        happyBankUtilMethods.authorizeAndGetVerifiedCustomer(uuid);
        CustomerAccountDetails customerAccountDetails =customerAccountDetailsRepo.findByAccountNumber(bankAccountNumber);
        if (customerAccountDetails==null){
            throw new CustomerAccountException("No Customer Account Details found for bank account " + bankAccountNumber);
        }
        Beneficiary beneficiary = BeneficiaryConverter.convertBeneficiaryDtoToBeneficiary(beneficiaryDTO);
        beneficiary.setAccount(customerAccountDetails);
        BeneficiaryEvent beneficiaryEvent = new BeneficiaryEvent(beneficiary);
        applicationEventPublisher.publishEvent(beneficiaryEvent);
        beneficiaryRepo.saveAndFlush(beneficiary);
        return "Beneficiary added successfully";
    }

    @Override
    public boolean beneficiaryAlreadyExists(String email, String phoneNumber) {
        Beneficiary beneficiary = beneficiaryRepo.findByBeneficiaryEmailOrPhoneNumber(email, phoneNumber);
        return beneficiary!=null;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean findAllBeneficiaryAccount() {
        List<Beneficiary> beneficiaries = beneficiaryRepo.findAll();
        log.info("beneficiary :: " + beneficiaries);
        return !beneficiaries.isEmpty();
    }

    @Override
    public String verifyBeneficiaryAccount(String uuid, String bankAccountNumber, BeneficiaryVerififyAccountDTO beneficiaryVerififyAccountDTO) {
        happyBankUtilMethods.authorizeAndGetVerifiedCustomer(uuid);
        CustomerAccountDetails customerAccountDetails = happyBankUtilMethods.validateCustomerAccountDetails(bankAccountNumber);
        Beneficiary beneficiary = customerAccountDetails.getBeneficiary();
        if (beneficiary==null){
            throw new BeneficiaryException("No Beneficiary found for the bank account " + bankAccountNumber);
        }
        String Otp = beneficiary.getOtp();
        if (Otp==null){
            throw new BeneficiaryException("Beneficiary have already verified the bank account " + bankAccountNumber);
        }
        if (!Otp.equals(beneficiaryVerififyAccountDTO.getOtp())){
            throw new BeneficiaryException("Invalid OTP");
        }
        if (Otp.equals(beneficiaryVerififyAccountDTO.getOtp())){
            beneficiary.setOtp(null);
            beneficiary.setAccountVerified(true);
            beneficiaryRepo.saveAndFlush(beneficiary);
        }
        return "Beneficiary account has successfully verified ";

    }
}
