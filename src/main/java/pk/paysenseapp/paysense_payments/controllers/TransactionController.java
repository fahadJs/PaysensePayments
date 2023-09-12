package pk.paysenseapp.paysense_payments.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pk.paysenseapp.paysense_payments.entities.Transaction;
import pk.paysenseapp.paysense_payments.services.TransactionService;

import java.util.List;

@RestController
@RequestMapping("/recentTransactions")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @GetMapping
    public List<Transaction> recentTransaction(@RequestParam String accountNumber){
        return transactionService.recentTransactions(accountNumber);
    }
}
