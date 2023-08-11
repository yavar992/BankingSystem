package myWallets.myWallets.listner;

import myWallets.myWallets.entity.CustomerAccountDetails;
import myWallets.myWallets.event.AccountOpenEvent;
import myWallets.myWallets.util.EmailSendarUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class AccountOpenEventListner  {

//    implements ApplicationListener<AccountOpenEvent>

    @Autowired
    EmailSendarUtil emailSendarUtil;

    @Async
    @EventListener
    public void onApplicationEvent(AccountOpenEvent event) {
         CustomerAccountDetails customerAccountDetails = (CustomerAccountDetails) event.getSource();
         String customerName = customerAccountDetails.getAccountHolderName();
         String accountNumber = customerAccountDetails.getAccountNo();
         String customerEmail = customerAccountDetails.getCustomer().getEmail();
//         String branchName = customerAccountDetails.getBankBranches().getBranchName();
//         String IFSCCode = customerAccountDetails.getBankBranches().getIFSCCode();
         String accountType = String.valueOf(customerAccountDetails.getBankAccountType());
//         String customerSupportEmail = customerAccountDetails.getBankBranches().getBranchEmail();
//         String customerSupportPhoneNumber = customerAccountDetails.getBankBranches().getBranchPhoneNumber();
         String accountOpeningDate = String.valueOf(customerAccountDetails.getAccountOpeningDate());
         String bankWebsite = "http://www.google.com";
         emailSendarUtil.sendEmailWithMultipleBodyLine(customerEmail , Arrays.asList(

                 "Dear " + customerName + ",\n\n" +
                         "Congratulations! We are thrilled to inform you that your account with Happy Bank has been successfully opened. " +
                         "We extend our warmest welcome to you as a valued member of the Happy Bank family.\n\n" +
                         "Account Details:\n" +
                         "Account Number: " + accountNumber + "\n" +
                         "Account Type: " + accountType + "\n" +
                         "Opening Date: " + accountOpeningDate + "\n" +
//                         "Branch Name: " + branchName + "\n" +
//                         "IFSC code " + IFSCCode + "\n" +
                         "At Happy Bank, we are committed to providing you with exceptional financial services that cater to your unique needs. " +
                         "Your new account opens the door to a world of convenient banking services, innovative digital tools, and expert financial guidance.\n\n" +
                         "You can now start enjoying the benefits of being a Happy Bank customer, including:\n" +
                         "- Hassle-free online banking for easy fund transfers and bill payments.\n" +
                         "- Access to our user-friendly mobile app for on-the-go account management.\n" +
                         "- Secure and reliable transactions to ensure your peace of mind.\n" +
                         "- Personalized financial advice to help you achieve your financial goals.\n\n" +
                         "To access your account online, please visit our website at " + bankWebsite + " and log in using your account credentials. " +
                         "Should you have any questions or require assistance, our dedicated customer support team is available to help you at " +
//                         customerSupportPhoneNumber + " or " + customerSupportEmail + ".\n\n" +
                         "Thank you for choosing Happy Bank for your banking needs. We look forward to serving you and assisting you in every step of your financial journey.\n\n" +
                         "Welcome aboard, and here's to a prosperous banking relationship!\n\n" +
                         "Best regards,\n" +
                         "Happy Bank"

         ) , ": Welcome to Happy Bank - Your Account is Now Open!");

    }
}
