package pk.paysenseapp.payensese_payments.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PinVerificationRequest {
    private String accountNumber;
    private String pin;
}
