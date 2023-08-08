package myWallets.myWallets.config;




import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;



@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "yavar ali khan",
                        email ="yavarkhan892300@gmail.com"
                ) ,
                description =  "Rest Api for the bank management system",
                title ="Bank Management Restful API",
                version = "3"
        ),
        servers = {
                @Server(
                        description = "LOCAL ENV",
                        url ="localhost:8484"
                ),
                @Server(
                        description =  "PROD ENV",
                        url = "localhost:8484/**"
                )
        }
)
public class OpenApiConfig {

}
