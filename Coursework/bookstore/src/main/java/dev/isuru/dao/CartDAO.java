package dev.isuru.dao;

import dev.isuru.model.Cart;
import dev.isuru.model.Cart.CartItem;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class CartDAO {
    // Key: customerId → Value: Cart
    private final Map<Integer, Cart> carts = new ConcurrentHashMap<>();
    private static volatile CartDAO instance;

    private CartDAO() {}

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

    public Cart getCartByCustomerId(int customerId) {
        return carts.getOrDefault(customerId, new Cart(customerId, new ArrayList<>()));
    }

    public boolean hasCart(int customerId) {
        return carts.containsKey(customerId);
    }

    public boolean hasBookInCart(int customerId, int bookId) {
        Cart cart = carts.get(customerId);
        if (cart == null) return false;

        synchronized (cart) {
            return cart.getCartItems().stream()
                    .anyMatch(item -> item.getBookId().equals(bookId));
        }
    }

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

    public void clearCart(int customerId) {
        carts.remove(customerId);
    }

    public List<CartItem> getAllItems(int customerId) {
        Cart cart = carts.get(customerId);
        if (cart == null) return Collections.emptyList();

        synchronized (cart) {
            return new ArrayList<>(cart.getCartItems());
        }
    }
}