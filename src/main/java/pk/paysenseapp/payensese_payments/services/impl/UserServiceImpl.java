package pk.paysenseapp.payensese_payments.services.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pk.paysenseapp.payensese_payments.dto.AccountInfo;
import pk.paysenseapp.payensese_payments.dto.BankResponse;
import pk.paysenseapp.payensese_payments.dto.UserRequest;
import pk.paysenseapp.payensese_payments.entities.User;
import pk.paysenseapp.payensese_payments.repositories.UserRepo;
import pk.paysenseapp.payensese_payments.services.UserService;
import pk.paysenseapp.payensese_payments.utils.AccountUtils;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserRepo userRepo;

    @Override
    public BankResponse createAccount(UserRequest userRequest) {
//        Creating an Account and adding new user into the database
//        Checking if the user has already an account.

        if (userRepo.existsByPhoneNumber(userRequest.getPhoneNumber())){
            BankResponse response = BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_EXIST_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_EXIST_MESSAGE)
                    .accountInfo(null)
                    .build();
            return response;
        }

        User user = new User().builder()
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .gender(userRequest.getGender())
                .city(userRequest.getCity())
                .address(userRequest.getAddress())
                .email(userRequest.getEmail())
                .username(userRequest.getUsername())
                .pin(userRequest.getPin())
                .phoneNumber(userRequest.getPhoneNumber())
                .status("ACTIVE")
                .accountBalance(0)
                .accountNumber(userRequest.getPhoneNumber())
                .build();

        User savedUser = userRepo.save(user);
        return BankResponse.builder()
                .responseCode(AccountUtils.ACCOUNT_CREATION_CODE)
                .responseMessage(AccountUtils.ACCOUNT_CREATION_MESSAGE)
                .accountInfo(AccountInfo.builder()
                        .accountNumber(savedUser.getAccountNumber())
                        .accountBalance(savedUser.getAccountBalance())
                        .accountName(savedUser.getFirstName()+" "+savedUser.getLastName())
                        .build())
                .build();
    }
}
