package dev.isuru.dao;

import dev.isuru.exception.CustomerNotFoundException;
import dev.isuru.model.Customer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerDAO implements DAO<Customer> {
    private static final Map<Integer, Customer> customers = new HashMap<>();
    private static int lastId = 0;


    @Override
    public Customer get(int id) {
        if (customers.containsKey(id)) {
            return customers.get(id);
        } else {
            throw new CustomerNotFoundException("Customer with the id " + id + " not found");
        }
    }

    @Override
    public List<Customer> getAll() {
        return new ArrayList<>(customers.values());
    }

    @Override
    public void add(Customer customer) {
        customer.setId(lastId);
        customers.put(lastId, customer);
        lastId++;
    }

    @Override
    public void update(int id, Customer customer) {
        if (customers.containsKey(id)) {
            customers.put(id, customer);
        } else {
            throw new CustomerNotFoundException("Customer with the id " + id + " not found");
        }
    }

    @Override
    public void delete(int id) {
        if (customers.containsKey(id)) {
            customers.remove(id);
        } else {
            throw new CustomerNotFoundException("Customer with the id " + id + " not found");
        }
    }
}
