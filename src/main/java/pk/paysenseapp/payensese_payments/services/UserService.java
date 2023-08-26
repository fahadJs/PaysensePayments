package pk.paysenseapp.payensese_payments.services;

import pk.paysenseapp.payensese_payments.dto.*;

public interface UserService {
    BankResponse createAccount(UserRequest userRequest);

    BankResponse balanceEnquiry(EnquiryRequest enquiryRequest);

    String nameEnquiry(EnquiryRequest enquiryRequest);

    BankResponse creditAmount(CreditRequest creditRequest);

    BankResponse debitAmount(DebitRequest debitRequest);

    BankResponse transferAmount(TransferRequest transferRequest);

    String pinVerification(PinVerificationRequest pinVerificationRequest);
}
