package dev.isuru.dao;

import dev.isuru.model.Customer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * DAO (Data Access Object) for managing Customer entities in-memory.
 * Uses a thread-safe ConcurrentHashMap and Singleton design pattern.
 */
public class CustomerDAO {
    private final ConcurrentMap<Integer, Customer> customers;
    private final AtomicInteger lastId;
    private static volatile CustomerDAO instance;

    // Private constructor to enforce singleton
    private CustomerDAO() {
        this.customers = new ConcurrentHashMap<>();
        this.lastId = new AtomicInteger(0);
    }

    /**
     * Returns the singleton instance of CustomerDAO.
     * Initializes the instance lazily and ensures thread safety.
     *
     * @return the singleton instance of CustomerDAO
     */
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

    /* Remove in production */
    static {
        CustomerDAO customerDAO = CustomerDAO.getInstance();
        customerDAO.add(new Customer(
                "Lex",
                "Freedmen",
                "lexy@gmail.com",
                "g32jhiybwi4"
        ));
        customerDAO.add(new Customer(
                "Terry",
                "Davis",
                "terry@terrydavis.com",
                "32hiyfdf324"
        ));
    }

    /**
     * Retrieves a customer by their ID.
     *
     * @param id the ID of the customer
     * @return the Customer object, or null if not found
     */
    public Customer get(int id) {
        return customers.get(id);
    }

    /**
     * Retrieves all customers.
     *
     * @return a list of all Customer objects
     */
    public List<Customer> getAll() {
        return new ArrayList<>(customers.values());
    }

    /**
     * Adds a new customer to the collection.
     * Assigns a unique ID automatically.
     *
     * @param customer the Customer object to add
     */
    public void add(Customer customer) {
        int newId = lastId.getAndIncrement();
        customer.setId(newId);
        customers.put(newId, customer);
    }

    /**
     * Updates an existing customer's information.
     *
     * @param id       the ID of the customer to update
     * @param customer the updated Customer object
     */
    public void update(int id, Customer customer) {
        customers.put(id, customer);
    }

    /**
     * Deletes a customer by their ID.
     *
     * @param id the ID of the customer to delete
     */
    public void delete(int id) {
        customers.remove(id);
    }

    /**
     * Checks whether a customer exists based on the customer object.
     *
     * @param customer the Customer object
     * @return true if the customer exists, false otherwise
     */
    public boolean contains(Customer customer) {
        return customers.containsKey(customer.getId());
    }

    /**
     * Checks whether a customer exists based on their ID.
     *
     * @param id the ID of the customer
     * @return true if the customer exists, false otherwise
     */
    public boolean contains(int id) {
        return customers.containsKey(id);
    }
}
