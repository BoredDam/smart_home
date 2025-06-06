package controller;

public class AlreadyRegisteredException extends Exception { 
    public AlreadyRegisteredException(String errorMessage) {
        super(errorMessage);
    }
}
