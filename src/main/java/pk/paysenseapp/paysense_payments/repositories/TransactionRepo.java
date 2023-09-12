package pk.paysenseapp.paysense_payments.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pk.paysenseapp.paysense_payments.entities.Transaction;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction, String> {
}
