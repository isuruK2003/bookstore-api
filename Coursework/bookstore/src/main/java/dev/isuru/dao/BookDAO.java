package dev.isuru.dao;

import dev.isuru.model.Book;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookDAO {
    private static final Map<Integer, Book> books = new HashMap<>();
    private static int lastId = 0;

    public Book get(int id) {
        return books.get(id);
    }

    public List<Book> getBooksByAuthor(Integer authorId) {
        List<Book> result = new ArrayList<>();
        for (Book book: books.values()) {
            if (book.getAuthorId() == authorId) {
                result.add(book);
            }
        }
        return result;
    }

    public List<Book> getAll() {
        return new ArrayList<>(books.values());
    }

    public void add(Book book) {
        book.setId(lastId);
        books.put(lastId, book);
        lastId++;
    }

    public void update(int id, Book book) {
        books.put(id, book);
    }

    public void delete(int id) {
        books.remove(id);
    }

    public boolean contains(Book book) {
        return  books.containsKey(book.getId());
    }

    public boolean contains(int id) {
        return books.containsKey(id);
    }

    public void reduceStock(int bookId, int quantity) {
        Book book = books.get(bookId);
        book.setStock(book.getStock() - quantity);
    }

    public void increaseStock(int bookId, int quantity) {
        Book book = books.get(bookId);
        book.setStock(book.getStock() + quantity);
    }
}
