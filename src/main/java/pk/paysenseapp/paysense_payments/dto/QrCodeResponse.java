package pk.paysenseapp.paysense_payments.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QrCodeResponse {
    private String responseCode;
    private String responseMessage;
    private String destinationAccountNumber;
    private String destinationAccountName;
}
