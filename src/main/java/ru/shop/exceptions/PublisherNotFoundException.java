package ru.shop.exceptions;

public class PublisherNotFoundException extends RuntimeException{
    public PublisherNotFoundException(){
        super("Publisher not found");
    }
}
