package pk.paysenseapp.payensese_payments.services;

import pk.paysenseapp.payensese_payments.dto.TransactionDto;
import pk.paysenseapp.payensese_payments.entities.Transaction;

import java.util.List;

public interface TransactionService {
    void saveTransaction(TransactionDto transactionDto);

    List<Transaction> recentTransactions(String accountNumber);
}
