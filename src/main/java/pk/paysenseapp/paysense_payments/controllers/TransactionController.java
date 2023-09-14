package pk.paysenseapp.paysense_payments.controllers;

import lombok.extern.log4j.Log4j2;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pk.paysenseapp.paysense_payments.entities.Transaction;
import pk.paysenseapp.paysense_payments.services.TransactionService;

import java.util.List;

@RestController
@RequestMapping("/recentTransactions")
@Log4j2
@CrossOrigin("*")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @GetMapping
    public List<Transaction> recentTransaction(@RequestParam String accountNumber){
        log.info("recentTransaction GET Request is processing!"+ DateTime.now());
        return transactionService.recentTransactions(accountNumber);
    }
}
