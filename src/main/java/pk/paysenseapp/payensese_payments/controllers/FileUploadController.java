package pk.paysenseapp.payensese_payments.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pk.paysenseapp.payensese_payments.dto.FileUploadResponse;
import pk.paysenseapp.payensese_payments.services.FileUploadService;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/api/fileUpload")
public class FileUploadController {

    @Autowired
    private FileUploadService fileUploadService;
    @Value("${project.image}")
    private String path;

    @PutMapping("/nic/{accountNumber}")
    public FileUploadResponse uploadImageNic(@RequestParam MultipartFile image,
                                          @PathVariable String accountNumber){
        String nicPath = path + File.separator + "nic";
        return fileUploadService.uploadImageNic(nicPath,image,accountNumber);
    }

    @PutMapping("/face/{accountNumber}")
    public FileUploadResponse uploadImageFace(@RequestParam MultipartFile image,
                                          @PathVariable String accountNumber){
        String facePath = path + File.separator + "face";
        return fileUploadService.uploadImageFace(facePath,image,accountNumber);
    }
}
