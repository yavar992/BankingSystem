package myWallets.myWallets.serviceImpl;

import lombok.extern.slf4j.Slf4j;
import myWallets.myWallets.constant.HappyBankUtilMethods;
import myWallets.myWallets.entity.CustomerAccountDetails;
import myWallets.myWallets.entity.Wallet;
import myWallets.myWallets.repository.WalletRepo;
import myWallets.myWallets.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@Transactional
public class WalletServiceImpl implements WalletService {

    @Autowired
    private WalletRepo walletRepo;

    @Autowired
    HappyBankUtilMethods happyBankUtilMethods;

    @Override
    public String openWallet(String uuid, String accountNumber) {
        happyBankUtilMethods.authorizeAndGetVerifiedCustomer(uuid);
        CustomerAccountDetails customerAccountDetails = happyBankUtilMethods.validateCustomerAccountDetails(accountNumber);
        Wallet wallet = new Wallet();
        wallet.setWalletId(UUID.randomUUID().toString());
        wallet.setBalance(0.00);
        wallet.setOwner(customerAccountDetails);
        walletRepo.saveAndFlush(wallet);
        return "Wallet Opened successfully";
    }

    @Override
    public boolean walletAlreadyExists() {
        List<Wallet> wallets = walletRepo.findAll();
        return !wallets.isEmpty();
    }
}
