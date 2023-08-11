package myWallets.myWallets.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import myWallets.myWallets.DTO.ForgetPasswordDTO;
import myWallets.myWallets.DTO.Login;
import myWallets.myWallets.DTO.OptDTO;
import myWallets.myWallets.entity.Customer;
import myWallets.myWallets.exceptionHandling.UserAlreadyLoggedIn;
import myWallets.myWallets.repository.CurrentUserSessionRepo;
import myWallets.myWallets.service.CustomerService;
import myWallets.myWallets.service.LoginService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/api/v1/user")
public class LoginController {

    LoginService loginService;
    CustomerService customerService;
    CurrentUserSessionRepo currentUserSessionRepo;

    public LoginController(LoginService loginService, CustomerService customerService, CurrentUserSessionRepo currentUserSessionRepo) {
        this.loginService = loginService;
        this.customerService = customerService;
        this.currentUserSessionRepo = currentUserSessionRepo;
    }

    @PostMapping({"/login","/signin"})
    public ResponseEntity<?> login(@Valid @RequestBody Login login){
        try {
            String output = loginService.login(login);
            if (output!=null){
                return ResponseEntity.status(HttpStatus.OK).body(output);
            }
        } catch (UserAlreadyLoggedIn e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred during login");
    }

    //LOGOUT URL

    @GetMapping({"/logout","/signout"})
    public ResponseEntity<?> logout(@RequestParam ("UUID") String UUID){
        try {
            String customer = loginService.logout(UUID);
            if (customer!=null){
                return ResponseEntity.status(HttpStatus.OK).body(" logout successfully !");
            }
        }catch (Exception e){
            log.error("cannot logout due to " +e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cannot Logout !");
    }

    //find user by current user session
    @GetMapping("/{UUID}")
    public ResponseEntity<?> getUserByCurrentUserSession(@PathVariable("UUID") String UUID){
        try {
            Customer customer = customerService.findByUserCurrentSession(UUID);
            if (customer!=null){
                return ResponseEntity.status(HttpStatus.OK).body(customer);
            }
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
        return null;
    }

    //Forget Password

    @PostMapping("/forget-password")
    public ResponseEntity<?> forgetPassword(@RequestBody ForgetPasswordDTO forgetPasswordDTO){
        try {
            String resetPassword = loginService.resetPassword(forgetPasswordDTO);
            if (resetPassword!=null){
                return ResponseEntity.status(HttpStatus.OK).body("Otp Sent Successfully");
            }
        }catch (Exception e){
            log.info("cannot reset the password due to " +e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cannot reset the password");
    }

    //Verify Otp
    @PostMapping("/verifyOtp")
    public ResponseEntity<?> verifyOTP(@RequestBody OptDTO optDTO){
        try {
            if (optDTO.getOtp()==null && optDTO.getPassword()==null){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Plz Enter both email and password");
            }
            String verifyOTP = loginService.verifyOTP(optDTO);
            if (verifyOTP!=null){
                return ResponseEntity.status(HttpStatus.OK).body("Password Reset Successfully");
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Couldn't verify the otp");
    }


}
