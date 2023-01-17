package main.exception;

public class NotAllowed extends RuntimeException{
    public NotAllowed(String message){
        super(message);
    }
}
