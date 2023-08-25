package pk.paysenseapp.payensese_payments.services;

import pk.paysenseapp.payensese_payments.dto.TransactionDto;
import pk.paysenseapp.payensese_payments.entities.Transaction;

public interface TransactionService {
    void saveTransaction(TransactionDto transactionDto);
}
