package myWallets.myWallets.util;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZonedDateTime;

@Service
public class HelperClass {

    public static void main(String[] args) {
        System.out.println(ZonedDateTime.now());
        System.out.println(Instant.now());
    }


}
