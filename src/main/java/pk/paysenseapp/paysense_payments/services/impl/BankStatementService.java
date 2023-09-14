package pk.paysenseapp.paysense_payments.services.impl;

import lombok.extern.log4j.Log4j2;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;
import pk.paysenseapp.paysense_payments.entities.Transaction;
import pk.paysenseapp.paysense_payments.repositories.TransactionRepo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
@Log4j2
public class BankStatementService {

    private final TransactionRepo transactionRepo;

    public BankStatementService(TransactionRepo transactionRepo) {
        this.transactionRepo = transactionRepo;
    }

    public List<Transaction> generateStatement(String accountNumber, String startDate, String endDate){
        LocalDate start = LocalDate.parse(startDate,DateTimeFormatter.ISO_LOCAL_DATE);
        LocalDate end = LocalDate.parse(endDate, DateTimeFormatter.ISO_LOCAL_DATE);

        log.info("generateBankStatement GET Request successfully processed! "+ DateTime.now());
        return transactionRepo.findAll().stream()
                .filter(transaction -> transaction.getAccountNumber().equals(accountNumber))
                .filter(transaction -> transaction.getCreatedOn().isAfter(start))
                .filter(transaction -> transaction.getCreatedOn().isBefore(end)).toList();
    }
}
