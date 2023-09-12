package pk.paysenseapp.paysense_payments.services;

import org.springframework.web.multipart.MultipartFile;
import pk.paysenseapp.paysense_payments.dto.FileUploadResponse;

public interface FileUploadService {

    FileUploadResponse uploadImageNic(MultipartFile file, String accountNumber);
    FileUploadResponse uploadImageFace(MultipartFile file, String accountNumber);
}
