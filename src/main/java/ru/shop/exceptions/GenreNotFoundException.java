package ru.shop.exceptions;

public class GenreNotFoundException extends RuntimeException{
    public GenreNotFoundException(){
        super("Genre not found");
    }
}
