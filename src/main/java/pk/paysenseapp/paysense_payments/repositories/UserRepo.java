package pk.paysenseapp.paysense_payments.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pk.paysenseapp.paysense_payments.entities.User;

@Repository
public interface UserRepo extends JpaRepository<User,Long> {
     Boolean existsByPhoneNumber(String phoneNumber);

     Boolean existsByAccountNumber(String accountNumber);

     Boolean existsByUsername(String username);

     User findByAccountNumber(String accountNumber);

     User findByUsername(String username);
     User findByQrCode(String qrCodeId);
}
