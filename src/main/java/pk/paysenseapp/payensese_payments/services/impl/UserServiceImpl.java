package pk.paysenseapp.payensese_payments.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pk.paysenseapp.payensese_payments.dto.*;
import pk.paysenseapp.payensese_payments.entities.User;
import pk.paysenseapp.payensese_payments.repositories.UserRepo;
import pk.paysenseapp.payensese_payments.services.UserService;
import pk.paysenseapp.payensese_payments.utils.AccountUtils;

import java.math.BigDecimal;
import java.math.BigInteger;

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
                .accountBalance(BigDecimal.valueOf(0.0))
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

    @Override
    public BankResponse balanceEnquiry(EnquiryRequest enquiryRequest) {
        Boolean isAccountExist = userRepo.existsByAccountNumber(enquiryRequest.getAccountNumber());
        if (!isAccountExist){
            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_NOT_EXIST_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE)
                    .accountInfo(null)
                    .build();
        }

        User accountFound = userRepo.findByAccountNumber(enquiryRequest.getAccountNumber());
        return BankResponse.builder()
                .responseCode(AccountUtils.ACCOUNT_FOUND_CODE)
                .responseMessage(AccountUtils.ACCOUNT_FOUND_MESSAGE)
                .accountInfo(AccountInfo.builder()
                        .accountBalance(accountFound.getAccountBalance())
                        .accountNumber(accountFound.getAccountNumber())
                        .accountName(accountFound.getFirstName() +" "+ accountFound.getLastName())
                        .build())
                .build();
    }

    @Override
    public String nameEnquiry(EnquiryRequest enquiryRequest) {
        Boolean isAccountExist = userRepo.existsByAccountNumber(enquiryRequest.getAccountNumber());
        if (!isAccountExist){
            return AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE;
        }

        User accountFound = userRepo.findByAccountNumber(enquiryRequest.getAccountNumber());
        return accountFound.getFirstName() + " " + accountFound.getLastName();
    }

    @Override
    public BankResponse creditAmount(CreditRequest creditRequest) {
        Boolean isAccountExist = userRepo.existsByAccountNumber(creditRequest.getAccountNumber());
        if (!isAccountExist){
            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_NOT_EXIST_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE)
                    .accountInfo(null)
                    .build();
        }

        User userToCredit = userRepo.findByAccountNumber(creditRequest.getAccountNumber());
        userToCredit.setAccountBalance(userToCredit.getAccountBalance().add(creditRequest.getAmount()));

        userRepo.save(userToCredit);
        return BankResponse.builder()
                .responseCode(AccountUtils.ACCOUNT_CREDITED_CODE)
                .responseMessage(AccountUtils.ACCOUNT_CREDITED_MESSAGE)
                .accountInfo(AccountInfo.builder()
                        .accountBalance(userToCredit.getAccountBalance())
                        .accountNumber(creditRequest.getAccountNumber())
                        .accountName(userToCredit.getFirstName() +" "+ userToCredit.getLastName())
                        .build())
                .build();
    }

    @Override
    public BankResponse debitAmount(DebitRequest debitRequest) {

//        Check If the account exist
//        Check if the balance is sufficient for debit

        Boolean isAccountExist = userRepo.existsByAccountNumber(debitRequest.getAccountNumber());
        if (!isAccountExist){
            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_NOT_EXIST_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE)
                    .accountInfo(null)
                    .build();
        }

        User userToDebit = userRepo.findByAccountNumber(debitRequest.getAccountNumber());
        BigInteger availableBalance = userToDebit.getAccountBalance().toBigInteger();
        BigInteger debitAmount = debitRequest.getAmount().toBigInteger();

        if (availableBalance.intValue() < debitAmount.intValue()){
            return BankResponse.builder()
                    .responseCode(AccountUtils.INSUFFICIENT_BALANCE_CODE)
                    .responseMessage(AccountUtils.INSUFFICIENT_BALANCE_MESSAGE)
                    .accountInfo(null)
                    .build();
        }else {
            userToDebit.setAccountBalance(userToDebit.getAccountBalance().subtract(debitRequest.getAmount()));
            userRepo.save(userToDebit);

            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_DEBITED_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_DEBITED_MESSAGE)
                    .accountInfo(AccountInfo.builder()
                            .accountBalance(userToDebit.getAccountBalance())
                            .accountNumber(debitRequest.getAccountNumber())
                            .accountName(userToDebit.getFirstName() +" "+ userToDebit.getLastName())
                            .build())
                    .build();
        }
    }
}
