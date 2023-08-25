package pk.paysenseapp.payensese_payments.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pk.paysenseapp.payensese_payments.dto.TransactionDto;
import pk.paysenseapp.payensese_payments.entities.Transaction;
import pk.paysenseapp.payensese_payments.repositories.TransactionRepo;
import pk.paysenseapp.payensese_payments.services.TransactionService;
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
}
