package dev.isuru.exception;

public class CustomerNotFoundException extends RuntimeException{
    public CustomerNotFoundException(String message) {
        super(message);
    }

    public CustomerNotFoundException(int customerId) {
        super("Customer with id=" + customerId + " not found");
    }
}
