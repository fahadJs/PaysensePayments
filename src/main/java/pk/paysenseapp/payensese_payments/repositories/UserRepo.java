package pk.paysenseapp.payensese_payments.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pk.paysenseapp.payensese_payments.entities.User;

@Repository
public interface UserRepo extends JpaRepository<User,Long> {

     boolean existsByEmail(String email);
     boolean existsByPhoneNumber(String phoneNumber);
}
