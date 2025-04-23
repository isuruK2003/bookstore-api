package dev.isuru.dao;

import dev.isuru.model.Customer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

public class CustomerDAO {
    private final ConcurrentMap<Integer, Customer> customers;
    private final AtomicInteger lastId;
    private static volatile CustomerDAO instance;

    // Private constructor to enforce singleton
    private CustomerDAO() {
        this.customers = new ConcurrentHashMap<>();
        this.lastId = new AtomicInteger(0);
    }

    // Singleton instance retrieval with double-checked locking
    public static CustomerDAO getInstance() {
        if (instance == null) {
            synchronized (CustomerDAO.class) {
                if (instance == null) {
                    instance = new CustomerDAO();
                }
            }
        }
        return instance;
    }

    public Customer get(int id) {
        return customers.get(id);
    }

    public List<Customer> getAll() {
        return new ArrayList<>(customers.values());
    }

    public void add(Customer customer) {
        int newId = lastId.getAndIncrement();
        customer.setId(newId);
        customers.put(newId, customer);
    }

    public void update(int id, Customer customer) {
        customers.put(id, customer);
    }

    public void delete(int id) {
        customers.remove(id);
    }

    public boolean contains(Customer customer) {
        return customers.containsKey(customer.getId());
    }

    public boolean contains(int id) {
        return customers.containsKey(id);
    }
}