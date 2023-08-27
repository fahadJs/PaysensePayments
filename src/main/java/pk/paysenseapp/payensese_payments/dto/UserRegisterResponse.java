package pk.paysenseapp.payensese_payments.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterResponse {
    private String responseCode;
    private String responseMessage;
    private String accountNumber;
    private String status;
}
