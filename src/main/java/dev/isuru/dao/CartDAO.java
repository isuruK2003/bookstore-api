package dev.isuru.dao;

import dev.isuru.model.Cart;
import dev.isuru.model.Cart.CartItem;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * DAO (Data Access Object) for managing customer carts in-memory.
 * Uses a thread-safe ConcurrentHashMap and Singleton design pattern.
 */
public class CartDAO {
    // Key: customerId → Value: Cart
    private final Map<Integer, Cart> carts = new ConcurrentHashMap<>();
    private static volatile CartDAO instance;

    private CartDAO() {}

    /**
     * Returns the singleton instance of CartDAO.
     * Initializes the instance lazily and ensures thread safety.
     *
     * @return the singleton instance of CartDAO
     */
    public static CartDAO getInstance() {
        if (instance == null) {
            synchronized (CartDAO.class) {
                if (instance == null) {
                    instance = new CartDAO();
                }
            }
        }
        return instance;
    }

    /**
     * Adds a cart item to a customer's cart.
     * If the item already exists, increases its quantity.
     * Creates a new cart if one does not exist for the customer.
     *
     * @param customerId the ID of the customer
     * @param bookId     the ID of the book to add
     * @param quantity   the quantity of the book
     */
    public void addCartItem(int customerId, int bookId, int quantity) {
        Cart cart = carts.computeIfAbsent(customerId,
                id -> new Cart(customerId, Collections.synchronizedList(new ArrayList<>())));

        synchronized (cart) {
            for (CartItem item : cart.getCartItems()) {
                if (item.getBookId().equals(bookId)) {
                    item.setQuantity(item.getQuantity() + quantity);
                    return;
                }
            }
            cart.getCartItems().add(new CartItem(bookId, quantity));
        }
    }

    /**
     * Retrieves a customer's cart.
     * If no cart exists, returns an empty cart.
     *
     * @param customerId the ID of the customer
     * @return the customer's Cart
     */
    public Cart getCartByCustomerId(int customerId) {
        return carts.getOrDefault(customerId, new Cart(customerId, new ArrayList<>()));
    }

    /**
     * Checks whether a customer has a cart.
     *
     * @param customerId the ID of the customer
     * @return true if the customer has a cart, false otherwise
     */
    public boolean hasCart(int customerId) {
        return carts.containsKey(customerId);
    }

    /**
     * Checks whether a specific book exists in a customer's cart.
     *
     * @param customerId the ID of the customer
     * @param bookId     the ID of the book
     * @return true if the book is in the cart, false otherwise
     */
    public boolean hasBookInCart(int customerId, int bookId) {
        Cart cart = carts.get(customerId);
        if (cart == null) return false;

        synchronized (cart) {
            return cart.getCartItems().stream()
                    .anyMatch(item -> item.getBookId().equals(bookId));
        }
    }

    /**
     * Removes a specific cart item from a customer's cart.
     * Removes the entire cart if it becomes empty.
     *
     * @param customerId the ID of the customer
     * @param bookId     the ID of the book to remove
     */
    public void removeCartItem(int customerId, int bookId) {
        Cart cart = carts.get(customerId);
        if (cart == null) return;

        synchronized (cart) {
            cart.getCartItems().removeIf(item -> item.getBookId().equals(bookId));
            if (cart.getCartItems().isEmpty()) {
                carts.remove(customerId);
            }
        }
    }

    /**
     * Clears the entire cart of a customer.
     *
     * @param customerId the ID of the customer
     */
    public void clearCart(int customerId) {
        carts.remove(customerId);
    }

    /**
     * Retrieves all items from a customer's cart.
     *
     * @param customerId the ID of the customer
     * @return a list of CartItems in the customer's cart
     */
    public List<CartItem> getAllItems(int customerId) {
        Cart cart = carts.get(customerId);
        if (cart == null) return Collections.emptyList();

        synchronized (cart) {
            return new ArrayList<>(cart.getCartItems());
        }
    }
}
