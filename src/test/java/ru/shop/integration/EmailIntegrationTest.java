package ru.shop.integration;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmailIntegrationTest {
    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @Order(0)
    public void testSendBuyBookEmail(){
        String to = "Sergey2018sar@yandex.ru";
        double price = 1000.0;

        restTemplate.exchange("http://localhost:" + port + "/api/email/sendBuyBook?to=" + to + "&price=" + price, HttpMethod.POST, HttpEntity.EMPTY, Integer.class);

        assertTrue(true);
    }

    @Test
    @Order(1)
    public void testSendDeleteBookEmail(){
        String to = "Sergey2018sar@yandex.ru";
        double price = 1000.0;

        restTemplate.exchange("http://localhost:" + port + "/api/email/sendDeleteBook?to=" + to + "&price=" + price, HttpMethod.POST, HttpEntity.EMPTY, Integer.class);

        assertTrue(true);
    }
}
