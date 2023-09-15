package pk.paysenseapp.paysense_payments.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FileUploadResponse {

    private String responseCode;
    private String responseMessage;
    private String filePath;
}
