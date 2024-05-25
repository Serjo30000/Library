package ru.shop.exceptions;

public class RentalNotFoundException extends RuntimeException{
    public RentalNotFoundException(){
        super("Rental not found");
    }
}
