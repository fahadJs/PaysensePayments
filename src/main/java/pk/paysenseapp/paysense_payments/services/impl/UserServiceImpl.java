package pk.paysenseapp.paysense_payments.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pk.paysenseapp.paysense_payments.dto.*;
import pk.paysenseapp.paysense_payments.entities.User;
import pk.paysenseapp.paysense_payments.repositories.UserRepo;
import pk.paysenseapp.paysense_payments.services.EmailService;
import pk.paysenseapp.paysense_payments.services.TransactionService;
import pk.paysenseapp.paysense_payments.services.UserService;
import pk.paysenseapp.paysense_payments.utils.AccountUtils;

import java.math.BigDecimal;
import java.math.BigInteger;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserRepo userRepo;

    @Autowired
    private EmailService emailService;

    @Autowired
    private TransactionService transactionService;

    @Override
    public UserRegisterResponse registerUser(UserRegisterRequest userRegisterRequest) {
        User foundUser = userRepo.findByAccountNumber(userRegisterRequest.getPhoneNumber());
        User foundUsername = userRepo.findByUsername(userRegisterRequest.getUsername()+"@paysense");

        if (userRepo.existsByPhoneNumber(userRegisterRequest.getPhoneNumber())){
            UserRegisterResponse response = UserRegisterResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_EXIST_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_EXIST_MESSAGE)
                    .accountNumber(foundUser.getAccountNumber())
                    .status(foundUser.getStatus())
                    .build();
            return response;
        }

        if (userRepo.existsByUsername(userRegisterRequest.getUsername() + "@paysense")){
            UserRegisterResponse response = UserRegisterResponse.builder()
                    .responseCode(AccountUtils.USERNAME_EXIST_CODE)
                    .responseMessage(AccountUtils.USERNAME_EXIST_MESSAGE)
                    .accountNumber(foundUsername.getAccountNumber())
                    .status(foundUsername.getStatus())
                    .build();
            return response;
        }

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encryptedPin = bCryptPasswordEncoder.encode(userRegisterRequest.getPin());

        User user = new User().builder()
                .firstName("PENDING APPROVAL")
                .lastName("PENDING APPROVAL")
                .gender("PENDING APPROVAL")
                .city("PENDING APPROVAL")
                .address("PENDING APPROVAL")
                .email(userRegisterRequest.getEmail())
                .username(userRegisterRequest.getUsername() + "@paysense")
                .pin(encryptedPin)
                .phoneNumber(userRegisterRequest.getPhoneNumber())
                .status("PENDING APPROVAL")
                .qrCode(AccountUtils.generateQrId(userRegisterRequest.getPhoneNumber(),16))
                .faceImage("UPLOAD PENDING")
                .nicImage("UPLOAD PENDING")
                .accountBalance(BigDecimal.valueOf(0.0))
                .accountNumber(userRegisterRequest.getPhoneNumber())
                .build();

        User savedUser = userRepo.save(user);

//        Send Email Alert
        EmailDetails emailDetails = EmailDetails.builder()
                .recipient(savedUser.getEmail())
                .subject("ACCOUNT REGISTRATION SUCCESSFUL!")
                .messageBody("Your Account has been successfully registered!\nIt will take 24 to 48 hours to get get your account approval.\n\nYou will be Notified via email you have provided.")
                .build();
        emailService.sendEmailAlert(emailDetails);

        return UserRegisterResponse.builder()
                .responseCode(AccountUtils.ACCOUNT_REGISTRATION_SUCCESS_CODE)
                .responseMessage(AccountUtils.ACCOUNT_REGISTRATION_SUCCESS_MESSAGE)
                .accountNumber(savedUser.getAccountNumber())
                .status(savedUser.getStatus())
                .build();
    }

    @Override
    public BankResponse createAccount(UserRequest userRequest, String accountNumber) {
//        Creating an Account and Updating user into the database
//        Checking if the user has already an account.

        User foundUser = userRepo.findByAccountNumber(accountNumber);

        if (!userRepo.existsByPhoneNumber(accountNumber)){
            BankResponse response = BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_NOT_EXIST_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE)
                    .accountInfo(null)
                    .build();
            return response;
        }

        if (userRepo.existsByPhoneNumber(accountNumber)){
            BankResponse response = BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_EXIST_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_EXIST_MESSAGE)
                    .accountInfo(null)
                    .build();
            return response;
        }

        foundUser.setFirstName(userRequest.getFirstName());
        foundUser.setLastName(userRequest.getLastName());
        foundUser.setGender(userRequest.getGender());
        foundUser.setCity(userRequest.getCity());
        foundUser.setAddress(userRequest.getAddress());
        foundUser.setStatus("ACTIVE");

//        User user = new User().builder()
//                .firstName(userRequest.getFirstName())
//                .lastName(userRequest.getLastName())
//                .gender(userRequest.getGender())
//                .city(userRequest.getCity())
//                .address(userRequest.getAddress())
//                .status("ACTIVE")
//                .build();

        User savedUser = userRepo.save(foundUser);

//        Send Email Alert
        EmailDetails emailDetails = EmailDetails.builder()
                .recipient(savedUser.getEmail())
                .subject("ACCOUNT CREATION SUCCESSFUL!")
                .messageBody("Your Account has been successfully created!\n" +
                        "Account Details:\n" +
                        "Account Title: " + savedUser.getFirstName() + " " + savedUser.getLastName() + "\nAccount Number: " + savedUser.getAccountNumber())
                .build();
        emailService.sendEmailAlert(emailDetails);

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

//        DEBIT Email Notification
        EmailDetails creditAlert = EmailDetails.builder()
                .subject("ACCOUNT CREDITED")
                .recipient(userToCredit.getEmail())
                .messageBody("The amount of Rs " + creditRequest.getAmount() + " has been credited to your account! Your current balance is Rs: " + userToCredit.getAccountBalance())
                .build();

        emailService.sendEmailAlert(creditAlert);

//        Save Transaction
        TransactionDto creditTransaction = TransactionDto.builder()
                .accountNumber(userToCredit.getAccountNumber())
                .transactionType("CREDIT")
                .amount(creditRequest.getAmount())
                .build();
        transactionService.saveTransaction(creditTransaction);

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

//        DEBIT Email Notification
            EmailDetails debitAlert = EmailDetails.builder()
                    .subject("ACCOUNT DEBITED")
                    .recipient(userToDebit.getEmail())
                    .messageBody("The amount of Rs " + debitRequest.getAmount() + " has been debited from your account! Your current balance is Rs: " + userToDebit.getAccountBalance())
                    .build();

            emailService.sendEmailAlert(debitAlert);

//            Save Transaction
            TransactionDto debitTransaction = TransactionDto.builder()
                    .accountNumber(userToDebit.getAccountNumber())
                    .transactionType("DEBIT")
                    .amount(debitRequest.getAmount())
                    .build();
            transactionService.saveTransaction(debitTransaction);

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

    @Override
    public BankResponse transferAmount(TransferRequest transferRequest) {
//        Check if the amount debited is not more than the current account balance
        Boolean isDestinationAccountExist = userRepo.existsByAccountNumber(transferRequest.getDestinationAccountNumber());
        if (!isDestinationAccountExist){
            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_NOT_EXIST_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE)
                    .accountInfo(null)
                    .build();
        }

        User sourceAccountUser = userRepo.findByAccountNumber(transferRequest.getSourceAccountNumber());
        if (transferRequest.getAmount().compareTo(sourceAccountUser.getAccountBalance()) > 0){
            return BankResponse.builder()
                    .responseCode(AccountUtils.INSUFFICIENT_BALANCE_CODE)
                    .responseMessage(AccountUtils.INSUFFICIENT_BALANCE_MESSAGE)
                    .accountInfo(null)
                    .build();
        }

        sourceAccountUser.setAccountBalance(sourceAccountUser.getAccountBalance().subtract(transferRequest.getAmount()));
        userRepo.save(sourceAccountUser);

//        Save Transaction
        TransactionDto transferDebitTransaction = TransactionDto.builder()
                .accountNumber(sourceAccountUser.getAccountNumber())
                .transactionType("DEBIT")
                .amount(transferRequest.getAmount())
                .build();
        transactionService.saveTransaction(transferDebitTransaction);


        User destinationAccountUser = userRepo.findByAccountNumber(transferRequest.getDestinationAccountNumber());
        destinationAccountUser.setAccountBalance(destinationAccountUser.getAccountBalance().add(transferRequest.getAmount()));
        userRepo.save(destinationAccountUser);

//        Save Transaction
        TransactionDto transferCreditTransaction = TransactionDto.builder()
                .accountNumber(destinationAccountUser.getAccountNumber())
                .transactionType("CREDIT")
                .amount(transferRequest.getAmount())
                .build();
        transactionService.saveTransaction(transferCreditTransaction);

//        ***EMAIL NOTIFICATIONS!***

//        CREDIT Email Notification
        String destinationAccountUsername = destinationAccountUser.getFirstName() + " " + destinationAccountUser.getLastName();
        String sourceAccountUsername = sourceAccountUser.getFirstName() + " " + sourceAccountUser.getLastName();
        EmailDetails creditAlert = EmailDetails.builder()
                .subject("ACCOUNT CREDITED")
                .recipient(destinationAccountUser.getEmail())
                .messageBody("The amount of Rs " + transferRequest.getAmount() + " has been credited to your account from " + sourceAccountUsername + "! Your current balance is Rs: " + destinationAccountUser.getAccountBalance())
                .build();
        emailService.sendEmailAlert(creditAlert);

//        DEBIT Email Notification
        EmailDetails debitAlert = EmailDetails.builder()
                .subject("ACCOUNT DEBITED")
                .recipient(sourceAccountUser.getEmail())
                .messageBody("The amount of Rs " + transferRequest.getAmount() + " has been sent from your account to "+ destinationAccountUsername +"! Your current balance is Rs: " + sourceAccountUser.getAccountBalance())
                .build();
        emailService.sendEmailAlert(debitAlert);

        return BankResponse.builder()
                .responseCode(AccountUtils.TRANSFER_SUCCESSFUL_CODE)
                .responseMessage(AccountUtils.TRANSFER_SUCCESSFUL_MESSAGE)
                .accountInfo(null)
                .build();
    }

    @Override
    public BankResponse pinVerification(PinVerificationRequest pinVerificationRequest) {

        Boolean isAccountExist = userRepo.existsByAccountNumber(pinVerificationRequest.getAccountNumber());
        if (!isAccountExist){
            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_NOT_EXIST_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE)
                    .accountInfo(null)
                    .build();
        }

        User accountFound = userRepo.findByAccountNumber(pinVerificationRequest.getAccountNumber());
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        if (!bCryptPasswordEncoder.matches(pinVerificationRequest.getPin(),accountFound.getPin())){
            return BankResponse.builder()
                    .responseCode(AccountUtils.PIN_VERIFICATION_FAILED_CODE)
                    .responseMessage(AccountUtils.PIN_VERIFICATION_FAILED_MESSAGE)
                    .accountInfo(null)
                    .build();
        }
        return BankResponse.builder()
                .responseCode(AccountUtils.PIN_VERIFICATION_SUCCESS_CODE)
                .responseMessage(AccountUtils.PIN_VERIFICATION_SUCCESS_MESSAGE)
                .accountInfo(AccountInfo.builder()
                        .accountName(accountFound.getFirstName() + " " + accountFound.getLastName())
                        .accountNumber(accountFound.getAccountNumber())
                        .accountBalance(accountFound.getAccountBalance())
                        .build())
                .build();
    }

    @Override
    public QrCodeResponse qrCodePayment(QrCodeRequest qrCodeRequest) {
        User foundUser = userRepo.findByQrCode(qrCodeRequest.getQrCodeId());

        return QrCodeResponse.builder()
                .responseCode(AccountUtils.QR_EXIST_CODE)
                .responseMessage(AccountUtils.QR_EXIST_MESSAGE)
                .destinationAccountName(foundUser.getFirstName() + " " + foundUser.getLastName())
                .destinationAccountNumber(foundUser.getAccountNumber())
                .build();
    }
}