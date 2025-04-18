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

    public void update(Book book) {
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getId() == book.getId()) {
                books.put(i, book);
                return;
            }
        }
        throw new BookNotFoundException("Book with the id " + book.getId() + " not found");
    }
}
