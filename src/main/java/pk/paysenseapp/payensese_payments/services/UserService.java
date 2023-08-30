package pk.paysenseapp.payensese_payments.services;

import pk.paysenseapp.payensese_payments.dto.*;

public interface UserService {

    UserRegisterResponse registerUser(UserRegisterRequest userRegisterRequest);
    BankResponse createAccount(UserRequest userRequest, String accountNumber);

    BankResponse balanceEnquiry(EnquiryRequest enquiryRequest);

    String nameEnquiry(EnquiryRequest enquiryRequest);

    BankResponse creditAmount(CreditRequest creditRequest);

    BankResponse debitAmount(DebitRequest debitRequest);

    BankResponse transferAmount(TransferRequest transferRequest);

    BankResponse pinVerification(PinVerificationRequest pinVerificationRequest);

    QrCodeResponse qrCodePayment(QrCodeRequest qrCodeRequest);
}
