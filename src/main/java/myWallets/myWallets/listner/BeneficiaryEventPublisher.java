package myWallets.myWallets.listner;

import lombok.extern.slf4j.Slf4j;
import myWallets.myWallets.entity.Beneficiary;
import myWallets.myWallets.event.BeneficiaryEvent;
import myWallets.myWallets.repository.BeneficiaryRepo;
import myWallets.myWallets.util.EmailSendarUtil;
import myWallets.myWallets.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@Slf4j
public class BeneficiaryEventPublisher  {

    //implements ApplicationListener<BeneficiaryEvent>

    @Autowired
    EmailSendarUtil emailSendarUtil;

    @Autowired
    BeneficiaryRepo beneficiaryRepo;
    @Async
    @EventListener
    public void onApplicationEvent(BeneficiaryEvent event) {
        Beneficiary beneficiary = (Beneficiary) event.getSource();
        String accountHolderName = beneficiary.getAccount().getAccountHolderName();
        String beneficiaryName = beneficiary.getName();
        String relationship = beneficiary.getRelationship();
        String email = beneficiary.getEmail();
        String bankBranchAddress = beneficiary.getAccount().getCustomer().getBankBranches().getStreetAddress();
        String fullBankBranchAddress = beneficiary.getAccount().getCustomer().getBankBranches().getCity();
        String cd = beneficiary.getAccount().getCustomer().getBankBranches().getState();
        String postalCode = beneficiary.getAccount().getCustomer().getBankBranches().getPostalCode();
        String fullAddress = bankBranchAddress+" " + fullBankBranchAddress + " " + postalCode + " " + cd;
        String accountTye = beneficiary.getAccount().getBankAccountType().name();
        String bankName = beneficiary.getAccount().getBankAccount().getBankName();
        String customerEmail = beneficiary.getAccount().getCustomer().getBankBranches().getBranchEmail();
        String customerPhoneNumber = beneficiary.getAccount().getCustomer().getBankBranches().getBranchPhoneNumber();
        String OTP = String.valueOf(Validator.otp());
        String bankWebsite = "www.google.com";
        String bodyOfMail = "<html>" +
                "<head></head>" +
                "<body>" +
                "<p>Dear <strong>" + beneficiaryName + " </strong> </p>" +
                "<p>We hope this message finds you well. We are reaching out to inform you about an important matter related to your financial relationship with <strong>" + bankName + "</strong>. We are pleased to inform you that <strong>"  + accountHolderName + " </strong>, the account holder of <strong>" + accountTye + "</strong> at <strong>" + bankName + "</strong>, has expressed a desire to add you as a beneficiary to their account.</p>" +
                "<p>We understand that financial matters are of utmost importance, and this request indicates the trust and connection the account owner has with you. This designation as a beneficiary grants you certain privileges that allow you to access and manage the account funds under specific circumstances, such as in the unfortunate event of the account owner's passing.</p>" +
                "<p>To proceed with this process, we kindly request your confirmation and authorization. Please follow these steps:</p>" +
                "<p><strong> Beneficiary Details: </strong><br>" +
                "- Account Holder's Name: <strong>" + accountHolderName + "</strong><br>" +
                "- Beneficiary's Name: <strong>" +beneficiaryName +"</strong><br>" +
                "- Relationship: <strong>" + relationship +"</strong></p>" +
                "<p><strong>Authorization Process:</strong><br>" +
                "1. To ensure the security of your financial relationship, kindly use the provided One-Time Password (OTP) to validate your account. This OTP serves as a confirmation of your presence in authorizing your beneficiary account details:<br>" +
                "<strong>" + OTP + "</strong><br>" +
                "Please follow the on-screen instructions to confidently proceed and confirm your intent for this authorization.</p>" +
                "<p><a href=\"www.google.com\"> Authorization Link</a></p>" +
                "<p>Please note that your authorization is vital to the process and confirms your understanding of the potential role you may play in managing the account's assets.</p>" +
                "<p>At <strong>" + bankName + "</strong>, we prioritize security and privacy. Rest assured that all provided information will be treated with the highest level of confidentiality and in accordance with our strict privacy policies and legal obligations.</p>" +
                "<p>For any questions or concerns, our dedicated customer support team is here to assist you. Reach out to us at <a href=\"[Customer Support Email or Phone Number]\">[Customer Support Email or Phone Number]</a>.</p>" +
                "<p>Thank you for your attention to this important matter.</p>" +
                "<p>Best regards,<br>The <strong>" + bankName + " Team </strong></p>" +
                "<p>" +fullAddress + "<br>" + bankWebsite+ "<br>" + customerEmail + "<br>" + customerPhoneNumber+ "</p>" +
                "</body>" +
                "</html>";

        emailSendarUtil.sendEmailWithMultipleBodyLine(email, Arrays.asList(
        bodyOfMail
        ) , "Beneficiary Request: Verify Your Authorization");
        beneficiary.setOtp(OTP);
        beneficiaryRepo.saveAndFlush(beneficiary);
    }


}
