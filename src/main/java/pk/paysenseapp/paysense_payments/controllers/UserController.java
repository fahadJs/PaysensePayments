package pk.paysenseapp.paysense_payments.controllers;

import lombok.extern.log4j.Log4j2;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pk.paysenseapp.paysense_payments.dto.*;
import pk.paysenseapp.paysense_payments.services.UserService;

@RestController
@RequestMapping("/api/user")
@Log4j2
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/registerUser")
    public UserRegisterResponse registerResponse(@RequestBody UserRegisterRequest registerRequest){
        log.info("registerUser POST Request is processing!"+ DateTime.now());
        return userService.registerUser(registerRequest);
    }

    @PutMapping("/createAccount/{accountNumber}")
    public BankResponse createAccount(@RequestBody UserRequest userRequest, @PathVariable String accountNumber){
        log.info("createAccount PUT Request is processing!"+ DateTime.now());
        return userService.createAccount(userRequest, accountNumber);
    }

    @GetMapping("/balanceEnquiry")
    public BankResponse balanceEnquiry(@RequestBody EnquiryRequest enquiryRequest){
        log.info("balanceEnquiry GET Request is processing!"+ DateTime.now());
        return userService.balanceEnquiry(enquiryRequest);
    }

    @GetMapping("/nameEnquiry")
    public String nameEnquiry(@RequestBody EnquiryRequest enquiryRequest){
        log.info("nameEnquiry GET Request is processing! "+ DateTime.now());
        return userService.nameEnquiry(enquiryRequest);
    }

    @PostMapping("/creditAccount")
    public BankResponse creditAccount(@RequestBody CreditRequest creditRequest){
        log.info("creditAccount POST Request is processing!"+ DateTime.now());
        return userService.creditAmount(creditRequest);
    }

    @PostMapping("/debitAccount")
    public BankResponse debitAccount(@RequestBody DebitRequest debitRequest){
        log.info("debitAccount POST Request is processing!"+ DateTime.now());
        return userService.debitAmount(debitRequest);
    }

    @PostMapping("/transfer")
    public BankResponse transfer(@RequestBody TransferRequest transferRequest){
        log.info("transfer POST Request is processing!"+ DateTime.now());
        return userService.transferAmount(transferRequest);
    }

    @GetMapping("/pinVerification")
    public BankResponse pinVerification(@RequestBody PinVerificationRequest pinVerificationRequest){
        log.info("pinVerification GET Request is processing!"+ DateTime.now());
        return userService.pinVerification(pinVerificationRequest);
    }

    @GetMapping("/qrCode")
    public QrCodeResponse qrCodeResponse(@RequestBody QrCodeRequest qrCodeRequest){
        log.info("qrCode GET Request is processing!"+ DateTime.now());
        return userService.qrCodePayment(qrCodeRequest);
    }

}
