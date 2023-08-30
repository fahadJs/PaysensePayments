package pk.paysenseapp.payensese_payments.dto;

import jakarta.annotation.Nonnull;
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
