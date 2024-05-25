package ru.shop.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.shop.dto.CheckDto;
import ru.shop.dto.TokenDto;
import ru.shop.services.BookService;
import ru.shop.services.EmailService;

import javax.mail.MessagingException;
import java.io.IOException;

@RestController
@RequestMapping("/api/email")
@AllArgsConstructor
@CrossOrigin(originPatterns = "*", methods = {RequestMethod.POST})
public class EmailController {
    private final EmailService emailService;

    @PostMapping("/sendBuyBook")
    public void sendBuyBookEmailPost(@RequestParam("to") String to, @RequestParam("price") double price) throws MessagingException, IOException {
        emailService.sendBuyBookEmail(to,price);
    }

    @PostMapping("/sendDeleteBook")
    public void sendDeleteBookEmailPost(@RequestParam("to") String to, @RequestParam("price") double price) throws MessagingException, IOException {
        emailService.sendDeleteBookEmail(to,price);
    }
}
