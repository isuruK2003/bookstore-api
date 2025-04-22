package dev.isuru.dao;

import dev.isuru.model.CartItem;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CartDAO {
    private static final Map<Integer, Map<Integer, CartItem>> carts = new HashMap<>();

    public void addCartItem(int customerId, int bookId, CartItem cartItem) {
        carts.computeIfAbsent(customerId, k -> new HashMap<>()).put(bookId, cartItem);
    }

    public Map<Integer, CartItem> getCartByCustomerId(int customerId) {
        return carts.getOrDefault(customerId, Collections.emptyMap());
    }

    public boolean hasCart(int customerId) {
        return carts.containsKey(customerId);
    }

    public boolean hasBookInCart(int customerId, int bookId) {
        return carts.containsKey(customerId) && carts.get(customerId).containsKey(bookId);
    }

    public void removeCartItem(int customerId, int bookId) {
        if (carts.containsKey(customerId)) {
            carts.get(customerId).remove(bookId);
            if (carts.get(customerId).isEmpty()) {
                carts.remove(customerId);
            }
        }
    }

    public void clearCart(int customerId) {
        carts.remove(customerId);
    }
}