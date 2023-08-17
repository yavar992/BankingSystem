package myWallets.myWallets.config;

import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
public class SpringSecurity {

    private static final String[] PUBLIC_URL = {
            "/api/v1/customer/singup",
            "/api/v1/customer/register",
            "/api/v1/user/singin",
            "/api/v1/user/login",
            "swagger-ui/**",
            "v3/api-docs/**"

    };

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Autowired
    AuthenticationEntryPoints  authenticationEntryPoint;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth->auth.requestMatchers(PUBLIC_URL).permitAll()
                        .anyRequest().authenticated()
                ).httpBasic(Customizer.withDefaults())
                .exceptionHandling(exception->exception.authenticationEntryPoint(authenticationEntryPoint));
                return httpSecurity.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

}
