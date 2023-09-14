package pk.paysenseapp.paysense_payments.controllers;

import lombok.extern.log4j.Log4j2;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pk.paysenseapp.paysense_payments.dto.FileUploadResponse;
import pk.paysenseapp.paysense_payments.services.FileUploadService;

import java.io.File;

@RestController
@RequestMapping("/api/fileUpload")
@Log4j2
@CrossOrigin("*")
public class FileUploadController {

    private final FileUploadService fileUploadService;

    public FileUploadController(FileUploadService fileUploadService) {
        this.fileUploadService = fileUploadService;
    }

    @PutMapping("/nic/{accountNumber}")
    public FileUploadResponse uploadImageNic(@RequestParam MultipartFile image,
                                          @PathVariable String accountNumber){
        log.info("uploadImageNic PUT Request is processing!"+ DateTime.now());
        return fileUploadService.uploadImageNic(image,accountNumber);
    }

    @PutMapping("/face/{accountNumber}")
    public FileUploadResponse uploadImageFace(@RequestParam MultipartFile image,
                                          @PathVariable String accountNumber){
        log.info("uploadImageFace PUT Request is processing!"+ DateTime.now());
        return fileUploadService.uploadImageFace(image,accountNumber);
    }
}
