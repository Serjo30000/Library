package ru.shop.exceptions;

public class GradeNotFoundException extends RuntimeException{
    public GradeNotFoundException(){
        super("Grade not found");
    }
}
