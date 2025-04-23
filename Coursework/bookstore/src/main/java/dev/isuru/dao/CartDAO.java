package dev.isuru.dao;

import dev.isuru.model.Cart;
import dev.isuru.model.Cart.CartItem;

import java.util.*;

public class CartDAO {
    // Key: customerId → Value: Cart
    private static final Map<Integer, Cart> carts = new HashMap<>();

    public void addCartItem(int customerId, int bookId, int quantity) {
        Cart cart = carts.computeIfAbsent(customerId, id -> new Cart(customerId, new ArrayList<>()));

        // Check if item already exists, then update quantity
        for (CartItem item : cart.getCartItems()) {
            if (item.getBookId().equals(bookId)) {
                item.setQuantity(item.getQuantity() + quantity);
                return;
            }
        }

        // If not found, add as new item
        cart.getCartItems().add(new CartItem(bookId, quantity));
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
        return cart.getCartItems().stream().anyMatch(item -> item.getBookId().equals(bookId));
    }

    public void removeCartItem(int customerId, int bookId) {
        Cart cart = carts.get(customerId);
        if (cart == null) return;

        cart.getCartItems().removeIf(item -> item.getBookId().equals(bookId));

        if (cart.getCartItems().isEmpty()) {
            carts.remove(customerId);
        }
    }

    public void clearCart(int customerId) {
        carts.remove(customerId);
    }

    public List<CartItem> getAllItems(int customerId) {
        return carts.containsKey(customerId) ? carts.get(customerId).getCartItems() : Collections.emptyList();
    }
}
