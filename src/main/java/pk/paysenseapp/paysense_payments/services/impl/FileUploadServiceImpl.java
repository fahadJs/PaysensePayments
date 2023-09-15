package pk.paysenseapp.paysense_payments.services.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.extern.log4j.Log4j2;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pk.paysenseapp.paysense_payments.dto.FileUploadResponse;
import pk.paysenseapp.paysense_payments.entities.User;
import pk.paysenseapp.paysense_payments.repositories.UserRepo;
import pk.paysenseapp.paysense_payments.services.FileUploadService;
import pk.paysenseapp.paysense_payments.utils.AccountUtils;

import java.io.IOException;
import java.util.UUID;

@Service
@Log4j2
public class FileUploadServiceImpl implements FileUploadService {

    @Value("${bucketName}")
    private String bucketName;

    private final UserRepo userRepo;
    private final AmazonS3 s3;

    public FileUploadServiceImpl(UserRepo userRepo, AmazonS3 s3) {
        this.userRepo = userRepo;
        this.s3 = s3;
    }

    @Override
    public FileUploadResponse uploadImageNic(MultipartFile file, String accountNumber) {

        User foundUser = userRepo.findByAccountNumber(accountNumber);

        if (!userRepo.existsByPhoneNumber(accountNumber)) {
            return FileUploadResponse.builder()
                    .responseCode(HttpStatus.OK.toString())
                    .responseMessage(AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE)
                    .build();
        }

        if (!foundUser.getNicImage().equals("UPLOAD PENDING")) {
            return FileUploadResponse.builder()
                    .responseCode(HttpStatus.OK.toString())
                    .responseMessage(AccountUtils.FILE_EXIST_MESSAGE)
                    .build();
        }

//        File name
        String name = file.getOriginalFilename();

//        Random File Name for security
        String randomId = "c-" + UUID.randomUUID();
        assert name != null;
        String fileName = randomId.concat(name.substring(name.lastIndexOf(".")));

        try {
            s3.putObject(bucketName, fileName, file.getInputStream(), new ObjectMetadata());
            foundUser.setNicImage(fileName);
            User savedUser = userRepo.save(foundUser);

            log.info("uploadImageNic PUT Request successfully processed!");
            return FileUploadResponse.builder()
                    .responseCode(HttpStatus.OK.toString())
                    .responseMessage(AccountUtils.FILE_UPLOAD_SUCCESS_MESSAGE)
                    .filePath(savedUser.getNicImage())
                    .build();
        } catch (IOException e) {
            log.info(e.getMessage());
            return FileUploadResponse.builder()
                    .responseCode(HttpStatus.OK.toString())
                    .responseMessage(AccountUtils.FILE_UPLOAD_FAILED_MESSAGE)
                    .filePath(null)
                    .build();
        }
    }

    @Override
    public FileUploadResponse uploadImageFace(MultipartFile file, String accountNumber) {
        User foundUser = userRepo.findByAccountNumber(accountNumber);

        if (!userRepo.existsByPhoneNumber(accountNumber)) {
            return FileUploadResponse.builder()
                    .responseCode(HttpStatus.OK.toString())
                    .responseMessage(AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE)
                    .build();
        }

        if (!foundUser.getFaceImage().equals("UPLOAD PENDING")) {
            return FileUploadResponse.builder()
                    .responseCode(HttpStatus.OK.toString())
                    .responseMessage(AccountUtils.FILE_EXIST_MESSAGE)
                    .build();
        }

//        File name
        String name = file.getOriginalFilename();

//        Random File Name for security
        String randomId = "f-" + UUID.randomUUID();
        assert name != null;
        String fileName = randomId.concat(name.substring(name.lastIndexOf(".")));

        try {
            s3.putObject(bucketName, fileName, file.getInputStream(), new ObjectMetadata());
            foundUser.setFaceImage(fileName);
            User savedUser = userRepo.save(foundUser);

            log.info("uploadImageFace PUT Request successfully processed!");
            return FileUploadResponse.builder()
                    .responseCode(HttpStatus.OK.toString())
                    .responseMessage(AccountUtils.FILE_UPLOAD_SUCCESS_MESSAGE)
                    .filePath(savedUser.getFaceImage())
                    .build();
        } catch (IOException e) {
            log.info(e.getMessage());
            return FileUploadResponse.builder()
                    .responseCode(HttpStatus.OK.toString())
                    .responseMessage(AccountUtils.FILE_UPLOAD_FAILED_MESSAGE)
                    .filePath(null)
                    .build();
        }
    }
}
