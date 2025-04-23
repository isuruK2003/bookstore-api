package dev.isuru.dao;

import dev.isuru.model.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

public class BookDAO {
    private final ConcurrentMap<Integer, Book> books;
    private final AtomicInteger lastId;
    private static volatile BookDAO instance;

    private BookDAO() {
        this.books = new ConcurrentHashMap<>();
        this.lastId = new AtomicInteger(0);
    }

    // Singleton instance retrieval with double-checked locking
    public static BookDAO getInstance() {
        if (instance == null) {
            synchronized (BookDAO.class) {
                if (instance == null) {
                    instance = new BookDAO();
                }
            }
        }
        return instance;
    }

    /*Remove in production*/
    static {
        BookDAO bookDAO = BookDAO.getInstance();
        bookDAO.add(new Book(
                "Atimic Habits",
                1,
                "978-3-16-148410-0",
                2016,
                10.99,
                5
        ));
        bookDAO.add(new Book(
                "A Brief History of Time",
                0,
                "978-3-16-148410-3",
                2016,
                12.99,
                30
        ));
    }

    public Book get(int id) {
        return books.get(id);
    }

    public List<Book> getBooksByAuthor(Integer authorId) {
        List<Book> result = new ArrayList<>();
        for (Book book : books.values()) {
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
        int newId = lastId.getAndIncrement();
        book.setId(newId);
        books.put(newId, book);
    }

    public void update(int id, Book book) {
        books.put(id, book);
    }

    public void delete(int id) {
        books.remove(id);
    }

    public boolean contains(Book book) {
        return books.containsKey(book.getId());
    }

    public boolean contains(int id) {
        return books.containsKey(id);
    }

    public void reduceStock(int bookId, int quantity) {
        Book book = books.get(bookId);
        if (book != null) {
            synchronized (book) {  // Ensure atomic stock update
                book.setStock(book.getStock() - quantity);
            }
        }
    }

    public void increaseStock(int bookId, int quantity) {
        Book book = books.get(bookId);
        if (book != null) {
            synchronized (book) {  // Ensure atomic stock update
                book.setStock(book.getStock() + quantity);
            }
        }
    }
}