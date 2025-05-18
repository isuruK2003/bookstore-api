package dev.isuru.exception;

public class CartNotFoundException extends RuntimeException {
    public CartNotFoundException(String message) {
        super(message);
    }
    public CartNotFoundException(int customerId) {
        super("Cart of the Customer with id=" + customerId + " not found");
    }
}
