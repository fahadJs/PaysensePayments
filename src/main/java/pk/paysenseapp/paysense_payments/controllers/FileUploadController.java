package pk.paysenseapp.paysense_payments.controllers;

import lombok.extern.log4j.Log4j2;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pk.paysenseapp.paysense_payments.dto.BankResponse;
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
    public ResponseEntity<FileUploadResponse> uploadImageNic(@RequestBody(required = false) MultipartFile image,
                                          @PathVariable String accountNumber){

        if (image != null){
            log.info("uploadImageNic PUT Request is processing!");
            FileUploadResponse response = fileUploadService.uploadImageNic(image, accountNumber);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }else {
            String errorMessage = "Request Body is null!";
            log.error(errorMessage);
            FileUploadResponse response = new FileUploadResponse();
            response.setResponseCode(HttpStatus.BAD_REQUEST.toString());
            response.setResponseMessage(errorMessage);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/face/{accountNumber}")
    public ResponseEntity<FileUploadResponse> uploadImageFace(@RequestBody(required = false) MultipartFile image,
                                          @PathVariable String accountNumber){
        if (image != null){
            log.info("uploadImageFace PUT Request is processing!");
            FileUploadResponse response = fileUploadService.uploadImageFace(image, accountNumber);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }else {
            String errorMessage = "Request Body is null!";
            log.error(errorMessage);
            FileUploadResponse response = new FileUploadResponse();
            response.setResponseCode(HttpStatus.BAD_REQUEST.toString());
            response.setResponseMessage(errorMessage);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
