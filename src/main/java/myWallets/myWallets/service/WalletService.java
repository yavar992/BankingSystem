package myWallets.myWallets.service;

public interface WalletService {
    String openWallet(String uuid, String accountNumber);

    boolean walletAlreadyExists();
}
