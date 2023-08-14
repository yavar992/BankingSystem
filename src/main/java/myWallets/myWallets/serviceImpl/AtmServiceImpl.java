package myWallets.myWallets.serviceImpl;

import lombok.extern.slf4j.Slf4j;
import myWallets.myWallets.DTO.ActivateAccountDTO;
import myWallets.myWallets.constant.HappyBankUtilMethods;
import myWallets.myWallets.entity.ATM;
import myWallets.myWallets.entity.Customer;
import myWallets.myWallets.entity.CustomerAccountDetails;
import myWallets.myWallets.event.ATMEvent;
import myWallets.myWallets.exceptionHandling.*;
import myWallets.myWallets.repository.AtmRepository;
import myWallets.myWallets.repository.CustomerAccountDetailsRepo;
import myWallets.myWallets.repository.CustomerRepo;
import myWallets.myWallets.service.AtmService;
import myWallets.myWallets.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Transactional
public class AtmServiceImpl implements AtmService {

    @Autowired
    private AtmRepository atmRepository;

    @Autowired
    CustomerAccountDetailsRepo customerAccountDetailsRepo;
    @Autowired
    private HappyBankUtilMethods happyBankUtilMethods;

    @Autowired
    CustomerRepo customerRepo;

    @Autowired
    public ApplicationEventPublisher applicationEventPublisher;

    private static final LocalDate atmExpirationDate = LocalDate.now().plusYears(5);
    private static final LocalDate dateTime = LocalDate.from(LocalDateTime.now().plusDays(10)); // Replace with your LocalDateTime object

    @Override
    public String getAtm(String uuid, String accountNumber) {
        try {
         Customer customer = happyBankUtilMethods.authorizeAndGetVerifiedCustomer(uuid);
         log.info("customer ::" + customer);
          CustomerAccountDetails  customerAccountDetails = getCustomerAccountDetails(uuid ,accountNumber);
        String accountNumbers = customerAccountDetails.getAccountNo();
            atmAlreadyExistsOrNope(accountNumbers);
            log.info("CustomerAccountDetails info :: " + customerAccountDetails);
            ATM atm = new ATM();
            atm.setAtmIssueAt(dateTime);
            atm.setAtmExpirationDate(atmExpirationDate);
            atm.setCustomerNameOnATM(customerAccountDetails.getAccountHolderName());
            atm.setCvv(String.valueOf(Validator.cvv()));
            atm.setCardNumber("HYBK00"+Validator.accountLast4digits() + Validator.otp()); //HYBK00 101891
            atm.setCustomerAccountDetails(customerAccountDetails);
            atm.setPin(null);
            atm.setVerified(false);
            atmRepository.saveAndFlush(atm);
            ATMEvent atmEvent = new ATMEvent(atm);
            applicationEventPublisher.publishEvent(atmEvent);
            return "atm request successful ";
        }catch (Exception e){
            throw e;
        }
    }

    @Override
    public String atmAlreadyExists(String accountNumber) {
        String customerAccountDetails = customerAccountDetailsRepo.findAtmByAccountNumber(accountNumber);
        log.info("CustomerAccountDetails ->: " + customerAccountDetails);
        if (customerAccountDetails!=null){
            throw new ATMAlreadyExist("You have already availed atm");
        }
        return customerAccountDetails;
    }

    @Override
    public String atmAlreadyExistsOrNot(String accountNumber) {
        String customerAccountDetails = customerAccountDetailsRepo.findAtmByAccountNumber(accountNumber);
        log.info("CustomerAccountDetails ->: " + customerAccountDetails);
        if (customerAccountDetails==null){
            throw new ATMAlreadyExist("You don't have any atm ");
        }
        return customerAccountDetails;
    }

    @Override
    public boolean atmAlreadyExistsOrNope(String accountNumber) {
        String customerAccountDetails = customerAccountDetailsRepo.findAtmByAccountNumber(accountNumber);
        log.info("CustomerAccountDetails: " + customerAccountDetails);
        if (customerAccountDetails!=null){
            throw new ATMAlreadyExist("you have already availed for the atm");
        }
        return customerAccountDetails==null;

    }

    @Override
    public String activateATM(String uuid, ActivateAccountDTO activateAccountDTO , String accountNumber) {
         happyBankUtilMethods.authorizeAndGetVerifiedCustomer(uuid);
      CustomerAccountDetails customerAccountDetails = happyBankUtilMethods.validateCustomerAccountDetails(accountNumber);
      log.info("customerAccountDetails ::" + customerAccountDetails) ;
      ATM atm =getATM(accountNumber);
      log.info("ATM " + atm);
      Long otp = atm.getAtmOtp();
      String cvv = atm.getCvv();

      if (activateAccountDTO.getOtp().equals(0) || activateAccountDTO.getCvv().length()==0){
          throw new InvalidOTPException("fields cannot be blank");
      }
        if (otp==null){
            throw new InvalidAtmDetails("You have already activate your ATM ");
        }
      if (!cvv.equals(activateAccountDTO.getCvv()) || !otp.equals(activateAccountDTO.getOtp())){
          throw new InvalidAtmDetails("Incorrect Data , Either cvv or otp is invalid");
      }
      if (cvv.equals(activateAccountDTO.getCvv()) && otp.equals(activateAccountDTO.getOtp())){
          atm.setAtmOtp(null);
          atm.setVerified(true);
          atmRepository.saveAndFlush(atm);
      }
      return "ATM Activate successfully";
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerAccountDetails getCustomerAccountDetails(String uuid, String accountNumber) {
       Customer customer = happyBankUtilMethods.authorizeAndGetVerifiedCustomer(uuid);
       log.info("customer ::" + customer);
       Long customerId = customer.getId();
       List<CustomerAccountDetails> customerAccountDetails = customerAccountDetailsRepo.findAllAccountByCustomerId(customerId);
       Optional<CustomerAccountDetails> customerAccountDetails1 = customerAccountDetails.stream()
               .filter(account->accountNumber.equals(account.getAccountNo()))
               .findFirst();
        if (customerAccountDetails1.isPresent()) {
            return customerAccountDetails1.get();
        } else {
            throw new CustomerAccountException("Customer account not found with account number: " + accountNumber);
        }
    }

    @Override
    public String generatePin(String uuid, String accountNumber, ActivateAccountDTO activateAccountDTO) {
        happyBankUtilMethods.authorizeAndGetVerifiedCustomer(uuid);
       customerAccountDetailsRepo.findByAccountNumber(accountNumber);
       ATM atm = getATM(accountNumber);
       Boolean isVerified = atm.isVerified();
       if (!isVerified){
           throw new InvalidAtmDetails("Plz verified you account first for generating your ATM Pin ");
       }
       String pin = atm.getPin();
       if (pin!=null){
           throw new InvalidAtmDetails("You have already generated your ATM Pin");
       }
       atm.setPin(activateAccountDTO.getPin());
       atmRepository.saveAndFlush(atm);
       return "Congratulations! You have successfully generate your ATM Pin";
    }


    public ATM getATM(String accountNumber){
       ATM atm = atmRepository.findByAccountNumber(accountNumber);
       if (atm==null){
           throw new ATMNotFound("No ATM found for account number " + accountNumber);
       }
       return atm;

    }

}


