package pk.paysenseapp.paysense_payments.controllers;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pk.paysenseapp.paysense_payments.dto.*;
import pk.paysenseapp.paysense_payments.services.UserService;

@RestController
@RequestMapping("/api/user")
@Log4j2
@CrossOrigin("*")
public class UserController {

    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }
    private final String postError = "Request Body can not be null!";
    private final String getError = "Parameter is not provided!";

    @PostMapping("/registerUser")
    public ResponseEntity<UserRegisterResponse> registerResponse(@RequestBody(required = false) UserRegisterRequest registerRequest){
        if (registerRequest != null){
            log.info("registerUser POST Request is processing!");
            UserRegisterResponse response = userService.registerUser(registerRequest);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }else {
            log.error(postError);
            UserRegisterResponse response = new UserRegisterResponse();
            response.setResponseCode(HttpStatus.BAD_REQUEST.toString());
            response.setResponseMessage(postError);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/createAccount/")
    public ResponseEntity<BankResponse> createAccount(@RequestBody(required = false) UserRequest userRequest,
                                       @RequestParam String accountNumber){
        if (userRequest != null){
            log.info("createAccount PUT Request is processing!");
            BankResponse response = userService.createAccount(userRequest, accountNumber);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }else {
            log.error(postError);
            BankResponse response = new BankResponse();
            response.setResponseCode(HttpStatus.BAD_REQUEST.toString());
            response.setResponseMessage(postError);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/balanceEnquiry")
    public ResponseEntity<BankResponse> balanceEnquiry(@RequestParam(required = false) String accountNumber){
        if (accountNumber != null){
            log.info("balanceEnquiry GET Request is processing!");
            BankResponse response = userService.balanceEnquiry(accountNumber);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }else {
            log.error(getError);
            BankResponse response = new BankResponse();
            response.setResponseCode(HttpStatus.BAD_REQUEST.toString());
            response.setResponseMessage(getError);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/nameEnquiry")
    public ResponseEntity<BankResponse> nameEnquiry(@RequestParam(required = false) String accountNumber){
        if (accountNumber != null){
            log.info("nameEnquiry GET Request is processing!");
            BankResponse response = userService.nameEnquiry(accountNumber);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }else {
            log.error(getError);
            BankResponse response = new BankResponse();
            response.setResponseCode(HttpStatus.BAD_REQUEST.toString());
            response.setResponseMessage(getError);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/creditAccount")
    public ResponseEntity<BankResponse> creditAccount(@RequestBody(required = false) CreditRequest creditRequest){
        if (creditRequest != null){
            log.info("creditAccount POST Request is processing!");
            BankResponse response = userService.creditAmount(creditRequest);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }else {
            log.error(postError);
            BankResponse response = new BankResponse();
            response.setResponseCode(HttpStatus.BAD_REQUEST.toString());
            response.setResponseMessage(postError);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/debitAccount")
    public ResponseEntity<BankResponse> debitAccount(@RequestBody(required = false) DebitRequest debitRequest){
        if (debitRequest != null){
            log.info("debitAccount POST Request is processing!");
            BankResponse response = userService.debitAmount(debitRequest);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }else {
            log.error(postError);
            BankResponse response = new BankResponse();
            response.setResponseCode(HttpStatus.BAD_REQUEST.toString());
            response.setResponseMessage(postError);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/transfer")
    public ResponseEntity<BankResponse> transfer(@RequestBody(required = false) TransferRequest transferRequest){
        if (transferRequest != null){
            log.info("transfer POST Request is processing!");
            BankResponse response = userService.transferAmount(transferRequest);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }else {
            log.error(postError);
            BankResponse response = new BankResponse();
            response.setResponseCode(HttpStatus.BAD_REQUEST.toString());
            response.setResponseMessage(postError);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/pinVerification")
    public ResponseEntity<PinVerificationResponse> pinVerification(@RequestBody(required = false) PinVerificationRequest pinVerificationRequest){
        if (pinVerificationRequest != null){
            log.info("pinVerification GET Request is processing!");
            PinVerificationResponse response = userService.pinVerification(pinVerificationRequest);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }else {
            log.error(postError);
            PinVerificationResponse response = new PinVerificationResponse();
            response.setResponseCode(HttpStatus.BAD_REQUEST.toString());
            response.setResponseMessage(postError);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/qrCode")
    public ResponseEntity<QrCodeResponse> qrCodeResponse(@RequestParam(required = false) String qrCodeId){
        if (qrCodeId != null){
            log.info("qrCode GET Request is processing!");
            QrCodeResponse response = userService.qrCodePayment(qrCodeId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }else {
            log.error(getError);
            QrCodeResponse response = new QrCodeResponse();
            response.setResponseCode(HttpStatus.BAD_REQUEST.toString());
            response.setResponseMessage(getError);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

}
