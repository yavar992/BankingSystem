package myWallets.myWallets.validator;

import myWallets.myWallets.DTO.Login;
import myWallets.myWallets.constant.StatusCode;
import myWallets.myWallets.entity.BankAccount;
import myWallets.myWallets.entity.BankBranches;
import myWallets.myWallets.entity.CurrentUserSession;
import myWallets.myWallets.entity.Customer;
import myWallets.myWallets.exceptionHandling.BankBranchesNotFoundException;
import myWallets.myWallets.exceptionHandling.BankNotFoundException;
import myWallets.myWallets.exceptionHandling.LoginException;
import myWallets.myWallets.exceptionHandling.UnverifiedCustomerException;

import java.util.Optional;

public interface ValidatorUtils {

    public static void validateUnverifiedCustomer(Customer customer) {
        if (customer != null && !customer.isVerified()) {
            throw new UnverifiedCustomerException(StatusCode.UNVERIFIED_ACCOUNT.getMessage());
        }
    }

    public static void validateLoggedInCustomer(Optional<Customer> customer, String uuid) {
        if (!customer.isPresent()) {
            throw new LoginException(StatusCode.USER_IS_NOT_LOGGED_IN.name() + " for the uuid " + uuid);
        }
    }
    public static void validateCurrentUserSession(Optional<CurrentUserSession> currentUserSession, String uuid) {
        if (!currentUserSession.isPresent()) {
            throw new LoginException("User Not Login  " + uuid);
        }
    }

    public static void validateBankBranch(Optional<BankBranches> bankBranches) {
        if (bankBranches == null || bankBranches.isEmpty()) {
            throw new BankBranchesNotFoundException(StatusCode.BRANCH_NOT_EXIST.getMessage());
        }
    }

    public static void validatePasswordAndEmail(Login login , Customer  customer){
    if (!login.getPassword().equals(customer.getPassword()) || !login.getEmail().equals(customer.getEmail())){
        throw new LoginException(StatusCode.WRONG_CREDENTIAL.getMessage());
        }

    }

    public static void validateLoggedInCustomer(Customer customer, String uuid) {
        if (customer==null) {
            throw new LoginException(StatusCode.USER_IS_NOT_LOGGED_IN.name() + " for the uuid " + uuid);
        }
    }

    public static void validateBank(Optional<BankAccount> bankAccount){
        if (bankAccount==null || bankAccount.isEmpty()){
            throw new BankNotFoundException("Bank Not Found");
        }
    }

}
