package dev.isuru.dao;

import dev.isuru.model.Author;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AuthorDAO {
    private static final Map<Integer, Author> authors = new HashMap<>();
    private static int lastId = 0;

    public Author get(int id) {
        return authors.get(id);
    }

    public List<Author> getAll() {
        return new ArrayList<>(authors.values());
    }

    public void add(Author author) {
        author.setId(lastId);
        authors.put(lastId, author);
        lastId++;
    }

    public void update(int id, Author obj) {
        authors.put(id, obj);
    }

    public void delete(int id) {
        authors.remove(id);
    }

    public boolean contains(Author author) {
        return  authors.containsKey(author.getId());
    }

    public boolean contains(int id) {
        return authors.containsKey(id);
    }
}
