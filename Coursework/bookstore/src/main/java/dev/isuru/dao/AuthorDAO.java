package dev.isuru.dao;

import dev.isuru.model.Author;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

public class AuthorDAO {
    private final ConcurrentMap<Integer, Author> authors;
    private final AtomicInteger lastId;
    private static volatile AuthorDAO authorDAO;

    private AuthorDAO() {
        this.authors = new ConcurrentHashMap<>();
        this.lastId = new AtomicInteger(0);
    }

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

    /*Remove in production*/
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

    public Author get(int id) {
        return authors.get(id);
    }

    public List<Author> getAll() {
        return new ArrayList<>(authors.values());
    }

    public void add(Author author) {
        int newId = lastId.getAndIncrement();
        author.setId(newId);
        authors.put(newId, author);
    }

    public void update(int id, Author obj) {
        authors.put(id, obj);
    }

    public void delete(int id) {
        authors.remove(id);
    }

    public boolean contains(Author author) {
        return authors.containsKey(author.getId());
    }

    public boolean contains(int id) {
        return authors.containsKey(id);
    }
}