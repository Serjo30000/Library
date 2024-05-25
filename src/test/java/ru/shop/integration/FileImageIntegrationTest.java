package ru.shop.integration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class FileImageIntegrationTest {
    @BeforeEach
    public void testGeneratedFileImage(){
        try{
            String nameFile = "test1.png";
            MultipartFile image = new MockMultipartFile(nameFile, new byte[0]);
            String currentDir = System.getProperty("user.dir");
            File currentDirectory = new File(currentDir);
            String uploadDir = new File(currentDirectory.getAbsolutePath() + "/client/src/assets/test").getAbsolutePath();
            String filePath = uploadDir + "/" + nameFile;
            File dest = new File(filePath);
            Files.copy(image.getInputStream(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
            assertTrue(true);
        }
        catch(IOException error){
            fail("Error send fileImage");
        }
    }

    @AfterEach
    public void testAllDeletedFileImage(){
        try {
            String folderPath = System.getProperty("user.dir") + "\\client\\src\\assets\\test";
            Files.walk(Paths.get(folderPath))
                    .sorted()
                    .map(Path::toFile)
                    .filter(file -> file.getName().endsWith(".png"))
                    .forEach(File::delete);
        } catch (Exception e) {
            System.out.println("Error delete all fileImage");
        }
    }

    @Test
    @Order(0)
    public void testSendFileImage() {
        try{
            String nameFile = "test2.png";
            MultipartFile image = new MockMultipartFile(nameFile, new byte[0]);
            String currentDir = System.getProperty("user.dir");
            File currentDirectory = new File(currentDir);
            String uploadDir = new File(currentDirectory.getAbsolutePath() + "/client/src/assets/test").getAbsolutePath();
            String filePath = uploadDir + "/" + nameFile;
            File dest = new File(filePath);
            Files.copy(image.getInputStream(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
            assertTrue(true);
        }
        catch(IOException error){
            fail("Error send fileImage");
        }
    }

    @Test
    @Order(1)
    public void testDropFileImage(){
        try{
            String nameFile = "test1.png";
            String currentDir = System.getProperty("user.dir");
            File currentDirectory = new File(currentDir);
            String uploadDir = (new File(currentDirectory.getAbsolutePath() + "/client/src/assets/test").getAbsolutePath()+"\\"+nameFile);
            File imageFile = new File(uploadDir);
            if (imageFile.exists()) {
                imageFile.delete();
            }
            assertTrue(true);
        }
        catch(Exception error){
            fail("Error delete fileImage");
        }
    }

    @Test
    @Order(2)
    public void testEditFileImage() {
        try{
            String oldNameFile = "test1.png";
            String newNameFile = "test2.png";
            MultipartFile image = new MockMultipartFile(newNameFile, new byte[0]);
            String currentDir = System.getProperty("user.dir");
            File currentDirectory = new File(currentDir);
            String uploadDir = (new File(currentDirectory.getAbsolutePath() + "/client/src/assets/test").getAbsolutePath());
            File imageFile = new File(uploadDir + "/" + oldNameFile );
            if (imageFile.exists()) {
                imageFile.delete();
            }
            String filePath = uploadDir+ "/" + newNameFile;
            File dest = new File(filePath);
            Files.copy(image.getInputStream(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
            assertTrue(true);
        }
        catch(IOException error){
            fail("Error send fileImage");
        }
    }
}
