package myWallets.myWallets.util;

import jakarta.annotation.PostConstruct;
import myWallets.myWallets.service.BankBranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class HelperClass {


    @Autowired
    BankBranchService bankBranchService;


    public void getBank(){
        bankBranchService.bankBranchesById(1l);
        System.out.println(bankBranchService.bankBranchesById(1l));
    }


    public static void main(String[] args) {
        LocalDateTime dateTime = LocalDateTime.now(); // Replace with your LocalDateTime object
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");
        String formattedDate = dateTime.format(formatter);
        System.out.println(formattedDate);
    }

}
