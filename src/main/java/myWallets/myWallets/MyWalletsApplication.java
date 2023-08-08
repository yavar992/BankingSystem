package myWallets.myWallets;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@EnableAsync
@EnableScheduling
@CrossOrigin("*")
public class MyWalletsApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyWalletsApplication.class, args);

	}

}



