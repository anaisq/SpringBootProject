package main.exception;

public class UserAlreadyOnDbException extends RuntimeException{
    public UserAlreadyOnDbException(String message){
        super(message);
    }
}
