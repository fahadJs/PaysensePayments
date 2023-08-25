package pk.paysenseapp.payensese_payments.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pk.paysenseapp.payensese_payments.entities.Transaction;

public interface TransactionRepo extends JpaRepository<Transaction, String> {

}
