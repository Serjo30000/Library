package ru.shop.services;

import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

@Service
@AllArgsConstructor
public class FileImageService {
    @PreAuthorize("hasRole('ADMIN')")
    public void sendFileImage(MultipartFile image, String nameFile) throws IOException {
        String currentDir = System.getProperty("user.dir");
        File currentDirectory = new File(currentDir);
        String uploadDir = new File(currentDirectory.getAbsolutePath() + "/client/src/assets").getAbsolutePath();
        String filePath = uploadDir + "/" + nameFile;
        File dest = new File(filePath);
        Files.copy(image.getInputStream(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void dropFileImage(String nameFile){
        String currentDir = System.getProperty("user.dir");
        File currentDirectory = new File(currentDir);
        String uploadDir = (new File(currentDirectory.getAbsolutePath() + "/client/src/assets").getAbsolutePath()+"\\"+nameFile);
        File imageFile = new File(uploadDir);
        if (imageFile.exists()) {
            imageFile.delete();
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void editFileImage(MultipartFile image, String oldNameFile, String newNameFile) throws IOException {
        String currentDir = System.getProperty("user.dir");
        File currentDirectory = new File(currentDir);
        String uploadDir = (new File(currentDirectory.getAbsolutePath() + "/client/src/assets").getAbsolutePath());
        File imageFile = new File(uploadDir + "/" + oldNameFile );
        if (imageFile.exists()) {
            imageFile.delete();
        }
        String filePath = uploadDir+ "/" + newNameFile;
        File dest = new File(filePath);
        Files.copy(image.getInputStream(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
    }
}
