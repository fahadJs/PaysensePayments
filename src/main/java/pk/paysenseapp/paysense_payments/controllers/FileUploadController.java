package pk.paysenseapp.paysense_payments.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pk.paysenseapp.paysense_payments.dto.FileUploadResponse;
import pk.paysenseapp.paysense_payments.services.FileUploadService;

import java.io.File;

@RestController
@RequestMapping("/api/fileUpload")
public class FileUploadController {

    private final FileUploadService fileUploadService;

    public FileUploadController(FileUploadService fileUploadService) {
        this.fileUploadService = fileUploadService;
    }

    @PutMapping("/nic/{accountNumber}")
    public FileUploadResponse uploadImageNic(@RequestParam MultipartFile image,
                                          @PathVariable String accountNumber){
        return fileUploadService.uploadImageNic(image,accountNumber);
    }

    @PutMapping("/face/{accountNumber}")
    public FileUploadResponse uploadImageFace(@RequestParam MultipartFile image,
                                          @PathVariable String accountNumber){
        return fileUploadService.uploadImageFace(image,accountNumber);
    }
}
