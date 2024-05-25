package ru.shop.e2e;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class AuthE2ETest {
    private int port = 3000;
    WebDriver driver;
    @Value("${testChromeDriver}")
    private String pathTestChromeDriver;

    @BeforeEach
    @Rollback(value = false)
    public void testSettingSet(){
        System.setProperty("webdriver.chrome.driver", pathTestChromeDriver);
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://localhost:" + port);
    }

    @Test
    @Order(0)
    public void testLogIn() {
        driver.get("http://localhost:" + port + "/login");
        List<WebElement> inputs = driver.findElements(By.tagName("input"));
        List<WebElement> buttons = driver.findElements(By.tagName("button"));
        inputs.get(0).sendKeys("DefaultAdmin");
        inputs.get(1).sendKeys("1");
        buttons.get(buttons.size()-1).click();

        assertTrue(true);

        driver.quit();
    }

    @Test
    @Order(1)
    public void testLogOut() {
        driver.get("http://localhost:" + port + "/login");
        List<WebElement> inputs = driver.findElements(By.tagName("input"));
        List<WebElement> buttons = driver.findElements(By.tagName("button"));
        inputs.get(0).sendKeys("DefaultAdmin");
        inputs.get(1).sendKeys("1");
        buttons.get(buttons.size()-1).click();

        driver.get("http://localhost:" + port);
        buttons = driver.findElements(By.tagName("button"));
        buttons.get(1).click();

        assertTrue(true);

        driver.quit();
    }
}
