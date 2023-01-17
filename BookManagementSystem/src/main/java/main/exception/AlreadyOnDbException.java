package main.exception;

public class AlreadyOnDbException extends RuntimeException{
    public AlreadyOnDbException(String message){
        super(message);
    }
}
