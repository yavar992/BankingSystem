package myWallets.myWallets;

import myWallets.myWallets.config.AuthenticationEntryPoints;
import myWallets.myWallets.config.CustomUserDetailsService;
import myWallets.myWallets.config.OpenApiConfig;
import myWallets.myWallets.config.SpringSecurity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.CrossOrigin;

//@AutoConfiguration
//@ComponentScan(basePackages = {"myWallets.myWallets.*"})
//@Import({AuthenticationEntryPoints.class , CustomUserDetailsService.class , OpenApiConfig.class , SpringSecurity.class})
@SpringBootApplication
@EnableAsync
@EnableScheduling
@CrossOrigin("*")
public class MyWalletsApplication {

	@Bean
	public ServletWebServerFactory servletWebServerFactory(){
		return new TomcatServletWebServerFactory();
	}

	public static void main(String[] args) {
		SpringApplication.run(MyWalletsApplication.class, args);

	}

}



