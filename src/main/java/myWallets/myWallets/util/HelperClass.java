package myWallets.myWallets.util;

import jakarta.annotation.PostConstruct;
import myWallets.myWallets.service.BankBranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZonedDateTime;

@Service
public class HelperClass {


    @Autowired
    BankBranchService bankBranchService;


    public void getBank(){
        bankBranchService.bankBranchesById(1l);
        System.out.println(bankBranchService.bankBranchesById(1l));
    }

}
