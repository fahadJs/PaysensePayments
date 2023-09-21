package pk.paysenseapp.paysense_payments.services;

import pk.paysenseapp.paysense_payments.dto.*;

public interface UserService {

    UserRegisterResponse registerUser(UserRegisterRequest userRegisterRequest);

    BankResponse createAccount(UserRequest userRequest, String accountNumber);

    BankResponse balanceEnquiry(String accountNumber);

    BankResponse nameEnquiry(String accountNumber);

    BankResponse creditAmount(CreditRequest creditRequest);

    BankResponse debitAmount(DebitRequest debitRequest);

    BankResponse transferAmount(TransferRequest transferRequest);

    PinVerificationResponse pinVerification(PinVerificationRequest pinVerificationRequest);

    QrCodeResponse qrCodePayment(String qrCodeId);
}
