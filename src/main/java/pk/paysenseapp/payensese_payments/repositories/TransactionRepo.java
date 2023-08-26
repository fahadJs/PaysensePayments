package pk.paysenseapp.payensese_payments.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pk.paysenseapp.payensese_payments.entities.Transaction;

import java.util.Date;
import java.util.List;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction, String> {
}
