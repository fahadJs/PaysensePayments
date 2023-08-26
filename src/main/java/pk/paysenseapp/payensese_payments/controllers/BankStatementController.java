package pk.paysenseapp.payensese_payments.controllers;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pk.paysenseapp.payensese_payments.entities.Transaction;
import pk.paysenseapp.payensese_payments.services.impl.BankStatementService;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/bankStatement")
@AllArgsConstructor
public class BankStatementController {

    @Autowired
    private BankStatementService bankStatementService;

    @GetMapping()
    public List<Transaction> generateBankStatement(@RequestParam String accountNumber,
                                                   @RequestParam String startDate,
                                                   @RequestParam String endDate){
        List<Transaction> bankStatement = bankStatementService.generateStatement(accountNumber, startDate, endDate);
        return bankStatement;
    }
}
