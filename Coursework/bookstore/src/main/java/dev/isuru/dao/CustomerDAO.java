package dev.isuru.dao;

import dev.isuru.model.Customer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerDAO{
    private static final Map<Integer, Customer> customers = new HashMap<>();
    private static int lastId = 0;

    public Customer get(int id) {
        return customers.get(id);
    }

    public List<Customer> getAll() {
        return new ArrayList<>(customers.values());
    }

    public void add(Customer customer) {
        customer.setId(lastId);
        customers.put(lastId, customer);
        lastId++;
    }

    public void update(int id, Customer customer) {
        customers.put(id, customer);
    }

    public void delete(int id) {
        customers.remove(id);
    }

    public boolean contains(Customer customer) {
        return  customers.containsKey(customer.getId());
    }

    public boolean contains(int id) {
        return customers.containsKey(id);
    }
}
