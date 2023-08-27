package pk.paysenseapp.payensese_payments.services;

import org.springframework.web.multipart.MultipartFile;
import pk.paysenseapp.payensese_payments.dto.FileUploadResponse;

import java.io.IOException;

public interface FileUploadService {

    FileUploadResponse uploadImageNic(String path, MultipartFile file, String accountNumber);
    FileUploadResponse uploadImageFace(String path, MultipartFile file, String accountNumber);
}
