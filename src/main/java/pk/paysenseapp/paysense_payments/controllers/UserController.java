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
    private final String errorMessage = "Request Body can not be null!";

    @PostMapping("/registerUser")
    public ResponseEntity<UserRegisterResponse> registerResponse(@RequestBody(required = false) UserRegisterRequest registerRequest){
        if (registerRequest != null){
            log.info("registerUser POST Request is processing!");
            UserRegisterResponse response = userService.registerUser(registerRequest);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }else {
            log.error(errorMessage);
            UserRegisterResponse response = new UserRegisterResponse();
            response.setResponseCode(HttpStatus.BAD_REQUEST.toString());
            response.setResponseMessage(errorMessage);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/createAccount/{accountNumber}")
    public ResponseEntity<BankResponse> createAccount(@RequestBody(required = false) UserRequest userRequest,
                                       @PathVariable String accountNumber){
        if (userRequest != null){
            log.info("createAccount PUT Request is processing!");
            BankResponse response = userService.createAccount(userRequest, accountNumber);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }else {
            log.error(errorMessage);
            BankResponse response = new BankResponse();
            response.setResponseCode(HttpStatus.BAD_REQUEST.toString());
            response.setResponseMessage(errorMessage);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/balanceEnquiry")
    public ResponseEntity<BankResponse> balanceEnquiry(@RequestBody(required = false) EnquiryRequest enquiryRequest){
        if (enquiryRequest != null){
            log.info("balanceEnquiry GET Request is processing!");
            BankResponse response = userService.balanceEnquiry(enquiryRequest);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }else {
            log.error(errorMessage);
            BankResponse response = new BankResponse();
            response.setResponseCode(HttpStatus.BAD_REQUEST.toString());
            response.setResponseMessage(errorMessage);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/nameEnquiry")
    public ResponseEntity<String> nameEnquiry(@RequestBody(required = false) EnquiryRequest enquiryRequest){
        if (enquiryRequest != null){
            log.info("nameEnquiry GET Request is processing!");
            return new ResponseEntity<>(userService.nameEnquiry(enquiryRequest), HttpStatus.OK);
        }else {
            log.error(errorMessage);
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/creditAccount")
    public ResponseEntity<BankResponse> creditAccount(@RequestBody(required = false) CreditRequest creditRequest){
        if (creditRequest != null){
            log.info("creditAccount POST Request is processing!");
            BankResponse response = userService.creditAmount(creditRequest);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }else {
            log.error(errorMessage);
            BankResponse response = new BankResponse();
            response.setResponseCode(HttpStatus.BAD_REQUEST.toString());
            response.setResponseMessage(errorMessage);
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
            log.error(errorMessage);
            BankResponse response = new BankResponse();
            response.setResponseCode(HttpStatus.BAD_REQUEST.toString());
            response.setResponseMessage(errorMessage);
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
            log.error(errorMessage);
            BankResponse response = new BankResponse();
            response.setResponseCode(HttpStatus.BAD_REQUEST.toString());
            response.setResponseMessage(errorMessage);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/pinVerification")
    public ResponseEntity<BankResponse> pinVerification(@RequestBody(required = false) PinVerificationRequest pinVerificationRequest){
        if (pinVerificationRequest != null){
            log.info("pinVerification GET Request is processing!");
            BankResponse response = userService.pinVerification(pinVerificationRequest);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }else {
            log.error(errorMessage);
            BankResponse response = new BankResponse();
            response.setResponseCode(HttpStatus.BAD_REQUEST.toString());
            response.setResponseMessage(errorMessage);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/qrCode")
    public ResponseEntity<QrCodeResponse> qrCodeResponse(@RequestBody(required = false) QrCodeRequest qrCodeRequest){
        if (qrCodeRequest != null){
            log.info("qrCode GET Request is processing!");
            QrCodeResponse response = userService.qrCodePayment(qrCodeRequest);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }else {
            log.error(errorMessage);
            QrCodeResponse response = new QrCodeResponse();
            response.setResponseCode(HttpStatus.BAD_REQUEST.toString());
            response.setResponseMessage(errorMessage);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

}
