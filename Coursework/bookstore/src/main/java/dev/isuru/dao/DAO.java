package dev.isuru.dao;

import java.util.List;

public interface DAO<T> {
    T get(int id) throws Exception;
    List<T> getAll() throws Exception;
    void add(T obj) throws Exception;
    void update(int id, T obj) throws Exception;
    void delete(int id) throws Exception;
}
