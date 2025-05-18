package dev.isuru.exception;

public class OutOfStockException extends RuntimeException{
    public OutOfStockException(String message){
        super(message);
    }

    public OutOfStockException() {
        super("Item is out of stock");
    }
}
