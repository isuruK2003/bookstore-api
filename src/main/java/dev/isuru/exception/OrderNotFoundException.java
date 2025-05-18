package dev.isuru.exception;

public class OrderNotFoundException extends RuntimeException{
    public OrderNotFoundException(String message) {
        super(message);
    }

    public OrderNotFoundException(int orderId) {
        super("Order with id=" + orderId + " not found");
    }
}
