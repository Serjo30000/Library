package ru.shop.exceptions;

public class RoleNotFoundException extends RuntimeException{
    public RoleNotFoundException(){
        super("Role not found");
    }
}
