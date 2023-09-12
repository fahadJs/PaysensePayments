package pk.paysenseapp.paysense_payments.services.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pk.paysenseapp.paysense_payments.dto.FileUploadResponse;
import pk.paysenseapp.paysense_payments.entities.User;
import pk.paysenseapp.paysense_payments.repositories.UserRepo;
import pk.paysenseapp.paysense_payments.services.FileUploadService;
import pk.paysenseapp.paysense_payments.utils.AccountUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@Service
public class FileUploadServiceImpl implements FileUploadService {

    @Value("${bucketName}")
    private String bucketName;

    @Value("${bucketUrl}")
    private String bucketUrl;

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
                    .responseCode(AccountUtils.ACCOUNT_NOT_EXIST_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE)
                    .build();
        }

        if (!foundUser.getNicImage().equals("UPLOAD PENDING")) {
            return FileUploadResponse.builder()
                    .responseCode(AccountUtils.FILE_EXIST_CODE)
                    .responseMessage(AccountUtils.FILE_EXIST_MESSAGE)
                    .build();
        }

//        File name
        String name = file.getOriginalFilename();

//        Random File Name for security
        String randomId = "c-" + UUID.randomUUID();
        String fileName = randomId.concat(name.substring(name.lastIndexOf(".")));

        try {
            s3.putObject(bucketName, fileName, file.getInputStream(), new ObjectMetadata());
            foundUser.setNicImage(bucketUrl + fileName);
            User savedUser = userRepo.save(foundUser);
            return FileUploadResponse.builder()
                    .responseCode(AccountUtils.FILE_UPLOAD_SUCCESS_CODE)
                    .responseMessage(AccountUtils.FILE_UPLOAD_SUCCESS_MESSAGE)
                    .filePath(savedUser.getNicImage())
                    .build();
        } catch (IOException e) {
            e.printStackTrace();
            return FileUploadResponse.builder()
                    .responseCode(AccountUtils.FILE_UPLOAD_FAILED_CODE)
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
                    .responseCode(AccountUtils.ACCOUNT_NOT_EXIST_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE)
                    .build();
        }

        if (!foundUser.getFaceImage().equals("UPLOAD PENDING")) {
            return FileUploadResponse.builder()
                    .responseCode(AccountUtils.FILE_EXIST_CODE)
                    .responseMessage(AccountUtils.FILE_EXIST_MESSAGE)
                    .build();
        }

//        File name
        String name = file.getOriginalFilename();

//        Random File Name for security
        String randomId = "f-" + UUID.randomUUID();
        String fileName = randomId.concat(name.substring(name.lastIndexOf(".")));

        try {
            s3.putObject(bucketName, fileName, file.getInputStream(), new ObjectMetadata());
            foundUser.setFaceImage(bucketUrl + fileName);
            User savedUser = userRepo.save(foundUser);
            return FileUploadResponse.builder()
                    .responseCode(AccountUtils.FILE_UPLOAD_SUCCESS_CODE)
                    .responseMessage(AccountUtils.FILE_UPLOAD_SUCCESS_MESSAGE)
                    .filePath(savedUser.getFaceImage())
                    .build();
        } catch (IOException e) {
            e.printStackTrace();
            return FileUploadResponse.builder()
                    .responseCode(AccountUtils.FILE_UPLOAD_FAILED_CODE)
                    .responseMessage(AccountUtils.FILE_UPLOAD_FAILED_MESSAGE)
                    .filePath(null)
                    .build();
        }

    }
}
