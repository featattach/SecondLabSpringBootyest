package ru.lvov.SecondLabSpringBoot.exception;

public class ValidationFailedException extends Exception{
    public ValidationFailedException(String message){ super(message);}
}