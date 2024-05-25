package ru.shop.exceptions;

public class AuthorNotFoundException extends RuntimeException{
    public AuthorNotFoundException(){
        super("Author not found");
    }
}
