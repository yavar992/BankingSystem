package myWallets.myWallets.util;

import jakarta.mail.MessagingException;

import java.util.List;

public interface EmailSendarUtil {


    void sendEmailWithAttachment(String toEmail ,
                                 String body ,
                                 String subjects ,
                                 String attachments);

    void sendEmailWithAttachmentInBulk(List<String> toEmail ,
                                       String body  ,
                                       String subbjects ,
                                       String attachments) throws MessagingException;


    void sendEmailWithAttachmentInBulkToExisTingUser(List<String> toEmail,
                                                     String body ,
                                                     String subbjects,
                                                     String attachment );

    void sendEmail(String toEmail , String body, String subjects);

    void sendDemoEventListnerEmail(String toEmail , String body ,  String subjects);

    void sendEmailWithMultipleBodyLine(String toEmail, List<String> body , String subjects);

    void sendEmailToMultipleUserWithMultipleLines(List<String> toEmail , List<String> body , String subjects) throws MessagingException;
}
