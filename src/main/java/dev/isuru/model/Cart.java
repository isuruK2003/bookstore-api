package dev.isuru.model;

import java.util.List;
import java.util.Objects;

public class Cart {

    public static class CartItem {
        private Integer bookId;
        private Integer quantity;

        public CartItem() {
        }

        public CartItem(Integer bookId, Integer quantity) {
            this.bookId = bookId;
            this.quantity = quantity;
        }

        public Integer getBookId() {
            return bookId;
        }

        public void setBookId(Integer bookId) {
            this.bookId = bookId;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }

        @Override
        public String toString() {
            return "CartItem{" +
                    "bookId=" + bookId +
                    ", quantity=" + quantity +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            CartItem cartItem = (CartItem) o;
            return Objects.equals(bookId, cartItem.bookId) &&
                    Objects.equals(quantity, cartItem.quantity);
        }

        @Override
        public int hashCode() {
            return Objects.hash(bookId, quantity);
        }
    }

    private int customerId;
    private List<CartItem> cartItems;

    public Cart() {
    }

    public Cart(int customerId, List<CartItem> cartItems) {
        this.customerId = customerId;
        this.cartItems = cartItems;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "customerId=" + customerId +
                ", cartItems=" + cartItems +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cart cart = (Cart) o;
        return customerId == cart.customerId &&
                Objects.equals(cartItems, cart.cartItems);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId, cartItems);
    }
}
