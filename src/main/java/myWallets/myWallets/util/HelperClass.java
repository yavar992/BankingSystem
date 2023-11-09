package myWallets.myWallets.util;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import myWallets.myWallets.service.BankBranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Service
@Slf4j
public class HelperClass {


    @Autowired
    BankBranchService bankBranchService;


    public void getBank(){
        bankBranchService.bankBranchesById(1l);
        System.out.println(bankBranchService.bankBranchesById(1l));
    }

    public static void main(String[] args) {
        LocalDateTime dateTime = LocalDateTime.now(); // Replace with your LocalDateTime object
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedDate = dateTime.format(formatter);
        System.out.println(formattedDate);
        Integer cvv = (int) ((Math.random()*900)+100);
        System.out.println(cvv);

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        System.out.println(passwordEncoder.encode("Yavar992"));

    }

    public static void infiniteRecursion(){
        System.out.println("infinite recursion");
    }
}
