package ru.shop.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.shop.services.FileImageService;

import javax.mail.MessagingException;
import java.io.IOException;

@RestController
@RequestMapping("/api/images")
@AllArgsConstructor
@CrossOrigin(originPatterns = "*", methods = {RequestMethod.POST})
public class FileImageController {
    private final FileImageService fileImageService;
    @PostMapping("/sendFileImage")
    public ResponseEntity<String> sendFileImage(@RequestParam("image") MultipartFile image, @RequestParam("nameFile") String nameFile) throws IOException {
        fileImageService.sendFileImage(image, nameFile);
        return ResponseEntity.ok("Image uploaded successfully");
    }

    @PostMapping("/dropFileImage")
    public ResponseEntity<String> dropFileImage(@RequestParam("nameFile") String nameFile) throws IOException {
        fileImageService.dropFileImage(nameFile);
        return ResponseEntity.ok("Image drop successfully");
    }

    @PostMapping("/editFileImage")
    public ResponseEntity<String> dropFileImage(@RequestParam("image") MultipartFile image,@RequestParam("oldNameFile") String oldNameFile,@RequestParam("newNameFile") String newNameFile) throws IOException {
        fileImageService.editFileImage(image,oldNameFile,newNameFile);
        return ResponseEntity.ok("Image edit successfully");
    }
}
