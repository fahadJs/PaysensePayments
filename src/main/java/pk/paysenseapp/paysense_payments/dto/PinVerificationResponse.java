package pk.paysenseapp.paysense_payments.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pk.paysenseapp.paysense_payments.entities.User;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PinVerificationResponse {
    private String responseCode;
    private String responseMessage;
    private User accountInfo;
}
