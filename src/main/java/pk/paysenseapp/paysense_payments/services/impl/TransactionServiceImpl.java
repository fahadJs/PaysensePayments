package pk.paysenseapp.paysense_payments.services.impl;

import lombok.extern.log4j.Log4j2;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import pk.paysenseapp.paysense_payments.dto.TransactionDto;
import pk.paysenseapp.paysense_payments.entities.Transaction;
import pk.paysenseapp.paysense_payments.repositories.TransactionRepo;
import pk.paysenseapp.paysense_payments.services.TransactionService;

import java.util.List;

@Service
@Log4j2
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepo transactionRepo;
    public TransactionServiceImpl(TransactionRepo transactionRepo) {
        this.transactionRepo = transactionRepo;
    }

    @Override
    public void saveTransaction(TransactionDto transactionDto) {
        Transaction transaction = Transaction.builder()
                .transactionType(transactionDto.getTransactionType())
                .accountNumber(transactionDto.getAccountNumber())
                .amount(transactionDto.getAmount())
                .status("SUCCESS")
                .build();

        transactionRepo.save(transaction);
        log.info("saveTransaction Request successfully processed! "+ DateTime.now());
    }

    @Override
    public List<Transaction> recentTransactions(String accountNumber) {
        log.info("recentTransaction GET Request successfully processed! "+ DateTime.now());
        return transactionRepo.findAll().stream()
                .filter(recentTransaction -> recentTransaction.getAccountNumber().equals(accountNumber)).toList();
    }
}
