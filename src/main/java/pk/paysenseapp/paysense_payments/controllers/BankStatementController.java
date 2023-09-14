package pk.paysenseapp.paysense_payments.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pk.paysenseapp.paysense_payments.entities.Transaction;
import pk.paysenseapp.paysense_payments.services.impl.BankStatementService;

import java.util.List;

@RestController
@RequestMapping("/bankStatement")
@AllArgsConstructor
@Log4j2
@CrossOrigin("*")
public class BankStatementController {

    @Autowired
    private BankStatementService bankStatementService;

    @GetMapping()
    public List<Transaction> generateBankStatement(@RequestParam String accountNumber,
                                                   @RequestParam String startDate,
                                                   @RequestParam String endDate){
        log.info("generateBankStatement GET Request is processing!"+ DateTime.now());
        return bankStatementService.generateStatement(accountNumber, startDate, endDate);
    }
}
