package pk.paysenseapp.payensese_payments.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pk.paysenseapp.payensese_payments.dto.*;
import pk.paysenseapp.payensese_payments.services.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public BankResponse createAccount(@RequestBody UserRequest userRequest){
        return userService.createAccount(userRequest);
    }

    @GetMapping("/balanceEnquiry")
    public BankResponse balanceEnquiry(@RequestBody EnquiryRequest enquiryRequest){
        return userService.balanceEnquiry(enquiryRequest);
    }

    @GetMapping("/nameEnquiry")
    public String nameEnquiry(@RequestBody EnquiryRequest enquiryRequest){
        return userService.nameEnquiry(enquiryRequest);
    }

    @PostMapping("/creditAccount")
    public BankResponse creditAccount(@RequestBody CreditRequest creditRequest){
        return userService.creditAmount(creditRequest);
    }

    @PostMapping("/debitAccount")
    public BankResponse debitAccount(@RequestBody DebitRequest debitRequest){
        return userService.debitAmount(debitRequest);
    }

    @PostMapping("/transfer")
    public BankResponse transfer(@RequestBody TransferRequest transferRequest){
        return userService.transferAmount(transferRequest);
    }

    @GetMapping("/pinVerification")
    public String pinVerification(@RequestBody PinVerificationRequest pinVerificationRequest){
        return userService.pinVerification(pinVerificationRequest);
    }

}
