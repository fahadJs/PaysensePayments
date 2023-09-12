package pk.paysenseapp.paysense_payments.services;

import pk.paysenseapp.paysense_payments.dto.TransactionDto;
import pk.paysenseapp.paysense_payments.entities.Transaction;

import java.util.List;

public interface TransactionService {
    void saveTransaction(TransactionDto transactionDto);

    List<Transaction> recentTransactions(String accountNumber);
}
