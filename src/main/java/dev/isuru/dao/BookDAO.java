package dev.isuru.dao;

import dev.isuru.model.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * DAO (Data Access Object) for managing Book objects in-memory.
 * Uses a thread-safe ConcurrentMap and Singleton design pattern.
 */
public class BookDAO {
    private final ConcurrentMap<Integer, Book> books;
    private final AtomicInteger lastId;
    private static volatile BookDAO instance;

    private BookDAO() {
        this.books = new ConcurrentHashMap<>();
        this.lastId = new AtomicInteger(0);
    }

    /**
     * Returns the singleton instance of BookDAO.
     * Initializes the instance lazily and ensures thread safety.
     *
     * @return the singleton instance of BookDAO
     */
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
                "A Brief History of Time",
                0,
                "978-3-16-148410-3",
                2016,
                12.99,
                30
        ));
        bookDAO.add(new Book(
                "Atomic Habits",
                1,
                "978-3-16-148410-0",
                2016,
                10.99,
                5
        ));
    }

    /**
     * Retrieves a Book by its ID.
     *
     * @param id the ID of the Book
     * @return the Book object if found, otherwise null
     */
    public Book get(int id) {
        return books.get(id);
    }

    /**
     * Retrieves all Books written by a specific Author.
     *
     * @param authorId the ID of the Author
     * @return a list of Books authored by the given Author
     */
    public List<Book> getBooksByAuthor(Integer authorId) {
        List<Book> result = new ArrayList<>();
        for (Book book : books.values()) {
            if (book.getAuthorId() == authorId) {
                result.add(book);
            }
        }
        return result;
    }

    /**
     * Retrieves a list of all Books.
     *
     * @return a list containing all Book objects
     */
    public List<Book> getAll() {
        return new ArrayList<>(books.values());
    }

    /**
     * Adds a new Book to the collection.
     * Automatically assigns a unique ID to the Book.
     *
     * @param book the Book object to be added
     */
    public void add(Book book) {
        int newId = lastId.getAndIncrement();
        book.setId(newId);
        books.put(newId, book);
    }

    /**
     * Updates an existing Book by ID.
     * Overwrites the existing Book with the provided object.
     *
     * @param id   the ID of the Book to update
     * @param book the new Book object
     */
    public void update(int id, Book book) {
        books.put(id, book);
    }

    /**
     * Deletes a Book by its ID.
     *
     * @param id the ID of the Book to delete
     */
    public void delete(int id) {
        books.remove(id);
    }

    /**
     * Checks whether a Book exists in the collection based on the Book object.
     *
     * @param book the Book object to check
     * @return true if the Book exists, false otherwise
     */
    public boolean contains(Book book) {
        return books.containsKey(book.getId());
    }

    /**
     * Checks whether a Book exists in the collection based on the ID.
     *
     * @param id the ID to check
     * @return true if a Book with the ID exists, false otherwise
     */
    public boolean contains(int id) {
        return books.containsKey(id);
    }

    /**
     * Reduces the stock quantity of a Book.
     * The update is performed atomically.
     *
     * @param bookId   the ID of the Book
     * @param quantity the quantity to reduce
     */
    public void reduceStock(int bookId, int quantity) {
        Book book = books.get(bookId);
        if (book != null) {
            synchronized (book) {  // Ensure atomic stock update
                book.setStock(book.getStock() - quantity);
            }
        }
    }

    /**
     * Increases the stock quantity of a Book.
     * The update is performed atomically.
     *
     * @param bookId   the ID of the Book
     * @param quantity the quantity to add
     */
    public void increaseStock(int bookId, int quantity) {
        Book book = books.get(bookId);
        if (book != null) {
            synchronized (book) {  // Ensure atomic stock update
                book.setStock(book.getStock() + quantity);
            }
        }
    }
}
