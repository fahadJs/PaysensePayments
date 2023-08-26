package pk.paysenseapp.payensese_payments.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pk.paysenseapp.payensese_payments.dto.TransactionDto;
import pk.paysenseapp.payensese_payments.entities.Transaction;
import pk.paysenseapp.payensese_payments.repositories.TransactionRepo;
import pk.paysenseapp.payensese_payments.services.TransactionService;

import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepo transactionRepo;

    @Override
    public void saveTransaction(TransactionDto transactionDto) {
        Transaction transaction = Transaction.builder()
                .transactionType(transactionDto.getTransactionType())
                .accountNumber(transactionDto.getAccountNumber())
                .amount(transactionDto.getAmount())
                .status("SUCCESS")
                .build();

        transactionRepo.save(transaction);
        System.out.println("Transaction Saved Successfully!");
    }

    @Override
    public List<Transaction> recentTransactions(String accountNumber) {
        List<Transaction> recentTransactionsList = transactionRepo.findAll().stream()
                .filter(recentTransaction -> recentTransaction.getAccountNumber().equals(accountNumber)).toList();
        return recentTransactionsList;
    }
}
