package dev.isuru.dao;

import dev.isuru.exception.AuthorNotFoundException;
import dev.isuru.model.Author;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AuthorDAO implements DAO<Author> {
    private static final Map<Integer, Author> authors = new HashMap<>();
    private static int lastId = 0;

    @Override
    public Author get(int id) {
        if (authors.containsKey(id)) {
            return authors.get(id);
        } else {
            throw new AuthorNotFoundException("Author with the id " + id + " not found");
        }
    }

    @Override
    public List<Author> getAll() {
        return new ArrayList<>(authors.values());
    }

    @Override
    public void add(Author author) {
        author.setId(lastId);
        authors.put(lastId, author);
        lastId++;
    }

    @Override
    public void update(int id, Author obj) {
        if (authors.containsKey(id)) {
            authors.put(id, obj);
        } else {
            throw new AuthorNotFoundException("Author with the id " + id + " not found");
        }
    }

    @Override
    public void delete(int id) {
        if (authors.containsKey(id)) {
            authors.remove(id);
        } else {
            throw new AuthorNotFoundException("Author with the id " + id + " not found");
        }
    }
}
