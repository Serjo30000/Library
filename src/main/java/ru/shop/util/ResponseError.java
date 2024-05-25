package ru.shop.util;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ResponseError {
    private String message;
    private String timestamp;
}
