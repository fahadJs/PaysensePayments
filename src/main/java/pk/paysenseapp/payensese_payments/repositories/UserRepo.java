package pk.paysenseapp.payensese_payments.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pk.paysenseapp.payensese_payments.entities.User;

public interface UserRepo extends JpaRepository<User, Long> {
}
