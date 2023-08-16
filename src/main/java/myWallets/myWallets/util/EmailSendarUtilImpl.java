package myWallets.myWallets.util;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
@Slf4j
public class EmailSendarUtilImpl implements EmailSendarUtil{


    @Autowired
    JavaMailSender javaMailSender;


    @Override
    public void sendEmailWithAttachment(String toEmail, String body, String subjects, String attachments) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("yavarkhan892300@gmail.com");
            helper.setTo(toEmail);
            helper.setSubject(subjects);
            helper.setText(body, true);
            FileSystemResource fileSystemResource = new FileSystemResource(new File(attachments));
            helper.addAttachment(fileSystemResource.getFilename(), fileSystemResource);
            javaMailSender.send(message);
            System.out.println("mail sent successfully");
            log.info(" mail with attachments sent successfully");
        } catch (Exception e) {
            log.info("can't send email with attachments");
        }
    }

    @Override
    public void sendEmailWithAttachmentInBulk(List<String> toEmail, String body, String subbjects, String attachments) throws MessagingException {

        try {
            for (String toemail : toEmail) {
                MimeMessage mimeMessage = javaMailSender.createMimeMessage();
                MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
                messageHelper.setFrom("yavarkhan892300@gmail.com");
                messageHelper.setSubject(subbjects);
                messageHelper.setText(body, true);
                messageHelper.setTo(toemail);
                FileSystemResource fileSystemResource = new FileSystemResource(new File(attachments));
                messageHelper.addAttachment(fileSystemResource.getFilename(), fileSystemResource);
                javaMailSender.send(mimeMessage);
                log.info("email send successfully");
            }

        } catch (Exception e) {
            log.info("failed to send email");
        }
    }

    @Override
    public void sendEmailWithAttachmentInBulkToExisTingUser(List<String> toEmail, String body, String subbjects, String attachment) {
        try {
            // List<String> allPlayerEmail = playersService.getAllMailsOfPlayer();
            for (String toemail : toEmail) {
                MimeMessage mimeMessage = javaMailSender.createMimeMessage();
                MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
                messageHelper.setFrom("yavarkhan892300@gmail.com");
                messageHelper.setSubject(subbjects);
                messageHelper.setText(body, true);
                messageHelper.setTo(toemail);
                FileSystemResource fileSystemResource = new FileSystemResource(new File(attachment));
                messageHelper.addAttachment(fileSystemResource.getFilename(), fileSystemResource);
                javaMailSender.send(mimeMessage);
                log.info("email send successfully");
            }

        } catch (Exception e) {
            log.info("failed to send email");
        }
    }

    @Override
    public void sendEmail(String toEmail, String body, String subjects) {
        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            // for(String toemail : toEmail){
            simpleMailMessage.setFrom("yavarkhan892300@gmail.com");
            simpleMailMessage.setTo(toEmail);
            simpleMailMessage.setText(body);
            simpleMailMessage.setSubject(subjects);
            javaMailSender.send(simpleMailMessage);
            log.info("mail sent successfully");
            // }
        } catch (Exception e) {
            log.info("cannot send email " + e);
        }
    }

    @Override
    public void sendDemoEventListnerEmail(String toEmail, String body, String subjects) {
        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom("yavarkhan892300@gmail.com");
            simpleMailMessage.setTo(toEmail);
            simpleMailMessage.setText(body);
            simpleMailMessage.setSubject(subjects);
            javaMailSender.send(simpleMailMessage);
        } catch (Exception e) {
            log.info("couldn't send the mail ");
        }
    }

    @Override
    public void sendEmailWithMultipleBodyLine(String toEmail, List<String> body, String subjects) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "utf-8");
            helper.setFrom("yavarkhan892300@gmail.com");
            helper.setTo(toEmail);
            helper.setText(String.join("\n", body), true);
            helper.setSubject(subjects);
            javaMailSender.send(mimeMessage);
        } catch (Exception exception) {
            log.info("cannot send the email due to the " + exception);
        }
    }

    @Override
    public void sendEmailToMultipleUserWithMultipleLines(List<String> toEmail, List<String> body, String subjects) throws MessagingException {
        try {
            for (String toemail : toEmail) {
                MimeMessage mimeMessage = javaMailSender.createMimeMessage();
                MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
                messageHelper.setFrom("yavarkhan892300@gmail.com");
                messageHelper.setSubject(subjects);
                messageHelper.setText(String.join("\n", body));
                messageHelper.setTo(toemail);
                javaMailSender.send(mimeMessage);
                log.info("mail send successfully");
            }
        } catch (Exception e) {
            log.info("Cannot send the email due to " + e.getMessage());
        }
    }

    public void sendEmailWithMultipleLine(String toEmail, List<String> body, String subjects) throws MessagingException {
        try {
                MimeMessage mimeMessage = javaMailSender.createMimeMessage();
                MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
                messageHelper.setFrom("yavarkhan892300@gmail.com");
                messageHelper.setSubject(subjects);
                messageHelper.setText(String.join("\n", body));
                messageHelper.setTo(toEmail);
                javaMailSender.send(mimeMessage);
                log.info("mail send successfully");
        } catch (Exception e) {
            log.info("Cannot send the email due to " + e.getMessage());
        }
    }
}
