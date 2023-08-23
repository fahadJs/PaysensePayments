package pk.paysenseapp.payensese_payments.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "tbl_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "firstname", columnDefinition = "varchar(20) default 'PA'")
    private String firstName;

    @Column(name = "lastname", columnDefinition = "varchar(20) default 'PA'")
    private String lastName;

    @Column(name = "gender", columnDefinition = "varchar(6) default 'PA'")
    private String gender;

    @Column(name = "city", columnDefinition = "varchar(20) default 'PA'")
    private String city;

    @Column(name = "address", columnDefinition = "varchar(100) default 'PA'")
    private String address;

    @Column(name = "email")
    private String email;

    @Column(name = "accNumber", columnDefinition = "varchar(11) default 'PA'")
    private String accountNumber;

    @Column(name = "username")
    private String username;

    @Column(name = "pinNumber")
    private String pin;

    @Column(name = "phoneNumber")
    private String phoneNumber;

    @Column(name = "accBal", columnDefinition = "integer default 0.0")
    private Integer accountBalance;

    @Column(name = "accStatus", columnDefinition = "varchar(2) default 'PA'")
    private String status;

    @CreationTimestamp
    @Column(name = "createdAt")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "modifiedAt")
    private LocalDateTime modifiedAt;
}
