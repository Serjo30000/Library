package ru.shop.exceptions;

public class UserLibraryNotFoundException extends RuntimeException{
    public UserLibraryNotFoundException(){
        super("UserLibrary not found");
    }
}
