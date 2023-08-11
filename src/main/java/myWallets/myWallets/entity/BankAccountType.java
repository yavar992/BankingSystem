package myWallets.myWallets.entity;

public enum BankAccountType  {

    SAVING,
    CURRENT,
    DEPOSIT;

    public static BankAccountType fromString(String value) {
        try {
            return valueOf(value.toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Invalid bank account type: " + value);
        }
    }

}
