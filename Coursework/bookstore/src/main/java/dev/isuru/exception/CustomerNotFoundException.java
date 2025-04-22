package dev.isuru.exception;

public class CustomerNotFoundException extends RuntimeException{
    public CustomerNotFoundException(String message) {
        super(message);
    }

    public CustomerNotFoundException(int customerId) {
        super("Cart of the Customer with id=" + customerId + " not found");
    }
}
