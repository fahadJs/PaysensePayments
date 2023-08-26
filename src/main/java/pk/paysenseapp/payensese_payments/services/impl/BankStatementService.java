package pk.paysenseapp.payensese_payments.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import pk.paysenseapp.payensese_payments.entities.Transaction;
import pk.paysenseapp.payensese_payments.repositories.TransactionRepo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.chrono.ChronoLocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Component
@AllArgsConstructor
public class BankStatementService {

    @Autowired
    private TransactionRepo transactionRepo;

    public List<Transaction> generateStatement(String accountNumber, String startDate, String endDate){
        LocalDate start = LocalDate.parse(startDate,DateTimeFormatter.ISO_LOCAL_DATE);
        LocalDate end = LocalDate.parse(endDate, DateTimeFormatter.ISO_LOCAL_DATE);
        List<Transaction> transactionList = transactionRepo.findAll().stream()
                .filter(transaction -> transaction.getAccountNumber().equals(accountNumber))
                .filter(transaction -> transaction.getCreatedOn().isAfter(start))
                .filter(transaction -> transaction.getCreatedOn().isBefore(end)).toList();

        return transactionList;
    }
}
