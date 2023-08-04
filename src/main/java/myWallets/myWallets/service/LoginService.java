package myWallets.myWallets.service;

import myWallets.myWallets.DTO.ForgetPasswordDTO;
import myWallets.myWallets.DTO.Login;
import myWallets.myWallets.DTO.OptDTO;

import javax.security.auth.login.LoginException;

public interface LoginService {
    String login(Login login);

    String logout(String uuid) throws LoginException;

    String resetPassword(ForgetPasswordDTO forgetPasswordDTO);

    String verifyOTP(OptDTO optDTO);
}
