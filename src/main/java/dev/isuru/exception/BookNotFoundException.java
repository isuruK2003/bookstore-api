package dev.isuru.exception;

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(String message) {
        super(message);
    }
    public BookNotFoundException(int bookId) {
        super("Book with id=" + bookId + " not found");
    }
}
