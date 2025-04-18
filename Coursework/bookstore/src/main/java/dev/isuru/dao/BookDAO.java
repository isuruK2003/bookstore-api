package dev.isuru.dao;

import dev.isuru.exception.BookNotFoundException;
import dev.isuru.model.Book;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookDAO implements DAO<Book> {
    private static final Map<Integer, Book> books = new HashMap<>();
    private static int lastId = 0;

    public List<Book> getAll() {
        return new ArrayList<>(books.values());
    }

    public void add(Book book) {
        book.setId(lastId);
        books.put(lastId, book);
        lastId++;
    }

    public void delete(int id) {
        if (books.containsKey(id)) {
            books.remove(id);
        } else {
            throw new BookNotFoundException("Book with the id " + id + " not found");
        }
    }

    public Book get(int id) {
        if (books.containsKey(id)) {
            return books.get(id);
        } else {
            throw new BookNotFoundException("Book with the id " + id + " not found");
        }
    }

    public void update(int id, Book book) {
        if (books.containsKey(id)) {
            books.put(id, book);
        } else {
            throw new BookNotFoundException("Book with the id " + id + " not found");
        }
    }
}
