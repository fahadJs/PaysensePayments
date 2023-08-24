package pk.paysenseapp.payensese_payments.services;

import org.springframework.stereotype.Service;
import pk.paysenseapp.payensese_payments.dto.BankResponse;
import pk.paysenseapp.payensese_payments.dto.UserRequest;

public interface UserService {
    BankResponse createAccount(UserRequest userRequest);
}
