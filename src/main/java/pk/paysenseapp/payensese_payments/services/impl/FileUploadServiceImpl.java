package pk.paysenseapp.payensese_payments.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pk.paysenseapp.payensese_payments.dto.FileUploadResponse;
import pk.paysenseapp.payensese_payments.entities.User;
import pk.paysenseapp.payensese_payments.repositories.UserRepo;
import pk.paysenseapp.payensese_payments.services.FileUploadService;
import pk.paysenseapp.payensese_payments.utils.AccountUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@Service
public class FileUploadServiceImpl implements FileUploadService {
    @Autowired
    private UserRepo userRepo;
    @Override
    public FileUploadResponse uploadImageNic(String nicPath, MultipartFile file, String accountNumber){

        User foundUser = userRepo.findByAccountNumber(accountNumber);

        if (!userRepo.existsByPhoneNumber(accountNumber)){
            FileUploadResponse response = FileUploadResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_NOT_EXIST_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE)
                    .build();
            return response;
        }

        if (!foundUser.getNicImage().equals("UPLOAD PENDING")){
            FileUploadResponse response = FileUploadResponse.builder()
                    .responseCode(AccountUtils.FILE_EXIST_CODE)
                    .responseMessage(AccountUtils.FILE_EXIST_MESSAGE)
                    .build();
            return response;
        }

//        File name
        String name = file.getOriginalFilename();

//        Random File Name for security
        String randomId = UUID.randomUUID().toString();
        String randomFileId = randomId.concat(name.substring(name.lastIndexOf(".")));

//        Full Path
        String filePath = nicPath + File.separator + randomFileId;

//        Create Folder if not created
        File folder = new File(nicPath);
        if (!folder.exists()){
            folder.mkdir();
        }

//        Copy File
        try {
            Files.copy(file.getInputStream(), Path.of(filePath));
        } catch (IOException e) {
            e.printStackTrace();
            return FileUploadResponse.builder()
                    .responseCode(AccountUtils.FILE_UPLOAD_FAILED_CODE)
                    .responseMessage(AccountUtils.FILE_UPLOAD_FAILED_MESSAGE)
                    .filePath(null)
                    .build();
        }

        foundUser.setNicImage(filePath);
        User savedUser = userRepo.save(foundUser);
        return FileUploadResponse.builder()
                .responseCode(AccountUtils.FILE_UPLOAD_SUCCESS_CODE)
                .responseMessage(AccountUtils.FILE_UPLOAD_SUCCESS_MESSAGE)
                .filePath(savedUser.getNicImage())
                .build();
    }

    @Override
    public FileUploadResponse uploadImageFace(String facePath, MultipartFile file, String accountNumber) {
        User foundUser = userRepo.findByAccountNumber(accountNumber);

        if (!userRepo.existsByPhoneNumber(accountNumber)){
            FileUploadResponse response = FileUploadResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_NOT_EXIST_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE)
                    .build();
            return response;
        }

        if (!foundUser.getFaceImage().equals("UPLOAD PENDING")){
            FileUploadResponse response = FileUploadResponse.builder()
                    .responseCode(AccountUtils.FILE_EXIST_CODE)
                    .responseMessage(AccountUtils.FILE_EXIST_MESSAGE)
                    .build();
            return response;
        }

//        File name
        String name = file.getOriginalFilename();

//        Random File Name for security
        String randomId = UUID.randomUUID().toString();
        String randomFileId = randomId.concat(name.substring(name.lastIndexOf(".")));

//        Full Path
        String filePath = facePath + File.separator + randomFileId;

//        Create Folder if not created
        File folder = new File(facePath);
        if (!folder.exists()){
            folder.mkdir();
        }

//        Copy File
        try {
            Files.copy(file.getInputStream(), Path.of(filePath));
        } catch (IOException e) {
            e.printStackTrace();
            return FileUploadResponse.builder()
                    .responseCode(AccountUtils.FILE_UPLOAD_FAILED_CODE)
                    .responseMessage(AccountUtils.FILE_UPLOAD_FAILED_MESSAGE)
                    .filePath(null)
                    .build();
        }

        foundUser.setFaceImage(filePath);
        User savedUser = userRepo.save(foundUser);
        return FileUploadResponse.builder()
                .responseCode(AccountUtils.FILE_UPLOAD_SUCCESS_CODE)
                .responseMessage(AccountUtils.FILE_UPLOAD_SUCCESS_MESSAGE)
                .filePath(savedUser.getFaceImage())
                .build();
    }
}
