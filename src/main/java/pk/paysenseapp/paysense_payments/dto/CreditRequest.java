package pk.paysenseapp.paysense_payments.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreditRequest {
    private String accountNumber;
    private BigDecimal amount;
}