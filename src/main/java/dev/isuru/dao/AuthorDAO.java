package dev.isuru.dao;

import dev.isuru.model.Author;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * DAO (Data Access Object) for managing Author objects in-memory.
 * Uses a thread-safe ConcurrentMap and Singleton design pattern.
 */
public class AuthorDAO {
    private final ConcurrentMap<Integer, Author> authors;
    private final AtomicInteger lastId;
    private static volatile AuthorDAO authorDAO;

    private AuthorDAO() {
        this.authors = new ConcurrentHashMap<>();
        this.lastId = new AtomicInteger(0);
    }

    /**
     * Returns the singleton instance of AuthorDAO.
     * Initializes the instance lazily and ensures thread safety.
     *
     * @return the singleton instance of AuthorDAO
     */
    public static AuthorDAO getAuthorDAO() {
        if (authorDAO == null) {
            synchronized (AuthorDAO.class) {
                if (authorDAO == null) {
                    authorDAO = new AuthorDAO();
                }
            }
        }
        return authorDAO;
    }

    /*
    * Creates author objects when class is initialized.
    * This is to simulate a persistent data storage
    **/
    static {
        AuthorDAO authorDAO = AuthorDAO.getAuthorDAO();
        authorDAO.add(new Author(
                "Stephen",
                "Hawking",
                "Stephen William Hawking was an English theoretical physicist, cosmologist, and author who was director of research at the Centre for Theoretical Cosmology at the University of Cambridge."
        ));
        authorDAO.add(new Author(
                "James",
                "Clear",
                "James Clear (born 1986) is an American writer. He is best known for his book Atomic Habits."
        ));
    }

    /**
     * Retrieves an Author by its ID.
     *
     * @param id the ID of the Author
     * @return the Author object if found, otherwise null
     */
    public Author get(int id) {
        return authors.get(id);
    }

    /**
     * Retrieves a list of all Authors.
     *
     * @return a list containing all Author objects
     */
    public List<Author> getAll() {
        return new ArrayList<>(authors.values());
    }

    /**
     * Adds a new Author to the collection.
     * Automatically assigns a unique ID to the Author.
     *
     * @param author the Author object to be added
     */
    public void add(Author author) {
        int newId = lastId.getAndIncrement();
        author.setId(newId);
        authors.put(newId, author);
    }

    /**
     * Updates an existing Author by ID.
     * Overwrites the existing Author with the provided object.
     *
     * @param id  the ID of the Author to update
     * @param obj the new Author object
     */
    public void update(int id, Author obj) {
        authors.put(id, obj);
    }

    /**
     * Deletes an Author by its ID.
     *
     * @param id the ID of the Author to delete
     */
    public void delete(int id) {
        authors.remove(id);
    }

    /**
     * Checks whether an Author exists in the collection based on the Author object.
     *
     * @param author the Author object to check
     * @return true if the Author exists, false otherwise
     */
    public boolean contains(Author author) {
        return authors.containsKey(author.getId());
    }

    /**
     * Checks whether an Author exists in the collection based on the ID.
     *
     * @param id the ID to check
     * @return true if an Author with the ID exists, false otherwise
     */
    public boolean contains(int id) {
        return authors.containsKey(id);
    }
}