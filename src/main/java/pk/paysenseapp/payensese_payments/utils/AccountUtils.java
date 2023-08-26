package pk.paysenseapp.payensese_payments.utils;


import java.security.SecureRandom;

public class AccountUtils {
    public static final String ACCOUNT_EXIST_CODE = "001";
    public static final String ACCOUNT_EXIST_MESSAGE = "Account already exist!";
    public static final String ACCOUNT_CREATION_CODE = "002";
    public static final String ACCOUNT_CREATION_MESSAGE = "Account successfully created!";

    public static final String ACCOUNT_NOT_EXIST_CODE = "003";
    public static final String ACCOUNT_NOT_EXIST_MESSAGE = "Account with provided account number does not exist!";

    public static final String ACCOUNT_FOUND_CODE = "004";
    public static final String ACCOUNT_FOUND_MESSAGE = "Account Founded!";

    public static final String ACCOUNT_CREDITED_CODE = "005";
    public static final String ACCOUNT_CREDITED_MESSAGE = "Account Credited successfully!";

    public static final String ACCOUNT_DEBITED_CODE = "006";
    public static final String ACCOUNT_DEBITED_MESSAGE = "Account Debited successfully!";

    public static final String INSUFFICIENT_BALANCE_CODE = "007";
    public static final String INSUFFICIENT_BALANCE_MESSAGE = "Insufficient balance!";

    public static final String TRANSFER_SUCCESSFUL_CODE = "008";
    public static final String TRANSFER_SUCCESSFUL_MESSAGE = "Transfer Successful!";

    public static String generateQrId(String accountNumber, Integer length){
        final String ALPHA_NUMERIC_STRING = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%^&*()";

        SecureRandom secureRandom = new SecureRandom();
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int index = secureRandom.nextInt(ALPHA_NUMERIC_STRING.length());
            char character = ALPHA_NUMERIC_STRING.charAt(index);
            builder.append(character);
        }
        return builder.append(accountNumber).toString();
    }

}
