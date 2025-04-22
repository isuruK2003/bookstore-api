package dev.isuru.exception;

public class AuthorNotFoundException extends RuntimeException {
    public AuthorNotFoundException(String message) {
        super(message);
    }

    public AuthorNotFoundException(int authorId) {
        super("Author with id=" + authorId + " not found");
    }
}
